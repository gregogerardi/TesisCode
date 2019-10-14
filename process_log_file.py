import re
import statistics
import pandas as pd
from functools import reduce
from matplotlib.pyplot import plot, show
import pylab
import math
import sys
import os
import itertools

DEFAULT_BATTERY_LEVEL = 9900000 # 99%
WINDOW_SIZE = 100000

DEVICES_ID = "devices_id"
DEV_JOIN = ";Device started"
DEV_LEFT = "Device stopped."
JOB_ARRIVED = "arrived_job"
JOB_ASSIGNED = ";Job assigned to"
JOB_INPUT_TRANSF_COMPLETED = ";Job transfer finished ;"
JOB_FINISHED = "The device finished the job;"
JOB_COMPLETED = "Result completely transferred"
JOB_FAILED = ";Device stopped. Failed jobs:"
BATT_UPDATE = "Battery update received from device"
NETWORK_ENERGY = "Net stats summary"


def load_log_data(logFileName):
    
    simlog_data = dict()
    devices = []
    initiated_devices = []
    arrived_jobs = []
    assigned_jobs = []
    input_transfered_jobs =[]
    finished_jobs = []
    completed_jobs = []
    battery_updates = []
    devices_left_cluster = []
    failed_jobs = []
    net_energy = []

    log_content = open(logFileName,"r")
    #content = log_file_handler.readlines()

    #it = iter(content)
    line = log_content.readline()
    while line:

        simtime = -1
        if re.match('^[0-9]+;', line):
            simtime = line.split(';')[0]

        if re.search(DEV_JOIN, line):
            deviceId = line.split(';')[1]
            devices.append(deviceId)
            init_batt = DEFAULT_BATTERY_LEVEL
            if len(line.split(';')) > 3:
                init_batt = line.split(';')[3]
            initiated_devices.append(tuple((simtime,deviceId,init_batt)))
        
        if re.search(';Job arrived', line):
            jobId = line.split(';')[3]
            arrived_jobs.append(tuple((simtime,jobId,line)))

        if re.search(JOB_ASSIGNED, line):
            jobId = line.split(';')[3]
            deviceId = str.strip(line.split(';')[4]) # To remove spaces from the end and the beginning
            assigned_jobs.append(tuple((simtime,jobId,deviceId,line)))

        # e.g., line format: 522416;PROXY;Job transfer finished ;1176;00000000-0000-0000-0000-30
        if re.search(JOB_INPUT_TRANSF_COMPLETED, line):
            jobId = line.split(';')[3]
            deviceId = str.strip(line.split(';')[4])
            input_transfered_jobs.append(tuple((simtime, jobId, deviceId, line)))

        #e.g., line format: 18730;00000000-0000-0000-0000-16;The device finished the job;Job [jobId=11];0:0.637
        if re.search(JOB_FINISHED, line):
            jobId = (line.split('=')[1]).split(']')[0]
            deviceId = line.split(';')[1]
            finished_jobs.append(tuple((simtime,jobId,deviceId,line)))

        #e.g., line format: 18718;00000000-0000-0000-0000-1e;Result completely transferred; jobId=;96
        if re.search(JOB_COMPLETED, line):
            jobId = str.strip(line.split(';')[4])
            deviceId = line.split(';')[1]
            completed_jobs.append(tuple((simtime,jobId,deviceId,line)))

        #e.g., line format: 248611834;PROXY;Battery update received from device 00000000-0000-0000-0000-1c value=197000
        if re.search(BATT_UPDATE, line):
            deviceId = line.split(' ')[5]
            batt_level = str.strip(line.split('=')[1])
            battery_updates.append(tuple((simtime, deviceId, batt_level, line)))

        #e.g., line format: 253422906;00000000-0000-0000-0000-1b;Device stopped. Failed jobs: 0 finished jobs 1628
        if re.search(DEV_LEFT, line):
            deviceId = line.split(';')[1]
            failed_jobs_field = str.strip(line.split(':')[1]).split(' ')[0]
            devices_left_cluster.append(tuple((simtime, deviceId, failed_jobs_field, line)))
            failed_jobs.append(tuple((simtime, deviceId, failed_jobs_field, line)))

        if re.search(NETWORK_ENERGY, line):
            line = log_content.readline()
            #skip log lines related to total data transferred.
            #Goes directly to entries of devices spent energy in networking activity
            while line and not re.search("Device ", line):
                line = log_content.readline()

            while line and re.search("Device ", line):
                deviceId = line.split(' ')[1]
                net_energy_spent = str.strip(line.split(' ')[2])
                net_energy.append(tuple((deviceId, net_energy_spent)))
                line = log_content.readline()

        line = log_content.readline()

    log_content.close()
    simlog_data[DEVICES_ID] = devices
    simlog_data[DEV_JOIN] = initiated_devices
    simlog_data[JOB_ARRIVED] = arrived_jobs
    simlog_data[JOB_ASSIGNED] = assigned_jobs
    simlog_data[JOB_INPUT_TRANSF_COMPLETED] = input_transfered_jobs
    simlog_data[JOB_FINISHED] = finished_jobs
    simlog_data[JOB_COMPLETED] = completed_jobs
    simlog_data[JOB_FAILED] = failed_jobs
    simlog_data[BATT_UPDATE] = battery_updates
    simlog_data[DEV_LEFT] = devices_left_cluster
    simlog_data[NETWORK_ENERGY] = net_energy

    return simlog_data


''' for each device_id in the devices list, looks into jobs_dict tuples, which have the form 
    (simtime,jobId,deviceId,line) and
    returns a dictionary with <device_id, count of jobs> data
    By calling the method with the appropriate dicts, it can be used to count assigned, finished,
    completed jobs of a log file'''
def count_jobs(devices, jobs_dict):
    ret_jobs = dict()
    for dev in devices:
        ret_jobs[dev] = len(
            [job_tuple[2] for job_tuple in jobs_dict if job_tuple[2] == dev])

    return ret_jobs

'''For each device returns the difference between the amount of jobs in the set A and the amount of jobs in the set B'''
def count_jobs_diff(devices, jobs_dictA, jobs_dictB):
    ret_jobs = dict()
    for dev in devices:
        amountA = len([job_tuple[2] for job_tuple in jobs_dictA if job_tuple[2] == dev])
        amountB = len([job_tuple[2] for job_tuple in jobs_dictB if job_tuple[2] == dev])
        ret_jobs[dev] = int(amountA) - int(amountB)

    return ret_jobs

''' looks into DEV_JOIN tuples with the form (simtime,deviceId,init_batt) and
    returns a dictionary with <device_id, battery level when device joins the cluster> data 
'''
def device_init_batt(logparts):
    dev_battdict = dict()
    for dev in logparts[DEVICES_ID]:
        dev_battdict[dev] = [dev_join_tuple[2] for dev_join_tuple in logparts[DEV_JOIN] if dev_join_tuple[1] == dev][0]

    return dev_battdict

'''Given a device and a list of job tuples,
    returns a python list of tuples with the format (simtime,jobId,deviceId,line)
    that match the device given as argument
'''
def device_jobs(dev,jobs):
    return [job_tuple for job_tuple in jobs if job_tuple[2] == dev]

'''Given a device list, a list of job tuples with the format (simtime,jobId,deviceId,line),
    and a list of dev_left tuples with the format (simtime, deviceId, failed_jobs, line)
    returns a python dict with devices as key and the battery level reported by the
    device just after the last job-related event happened
    IMPORTANT: this code assumes the device left only once in the whole log'''
def battlevel_after_last_job(devs,jobs, dev_left, batt_updates):
    dev_batt = dict()
    for dev in devs:
        #first check whether the device left because of a battery depletion.
        #A way to figure this out is check the failed jobs in the left event of the device
        dev_left_tuples = [dev_tuple for dev_tuple in dev_left if dev_tuple[1] == dev]

        if int(dev_left_tuples[-1][2]) > 0:
            dev_batt[dev] = 0
        else:
            #the following line assumes that jobs are chronologically ordered
            time_ordered_dev_jobs = device_jobs(dev,jobs)
            if len(time_ordered_dev_jobs) > 0:
                last_job_time = time_ordered_dev_jobs[-1][0]
                # From the list of battery updates of device dev whose sim time is greater than the sim time
                # of the last finished job assigned to that device, get the first item
                node_batt_updates = [batt_update_tuple[2] for batt_update_tuple in reversed(batt_updates)
                             if batt_update_tuple[1] == dev and int(batt_update_tuple[0]) >= int(last_job_time)]
                dev_batt[dev] = 0 if len(node_batt_updates) == 0 else int(node_batt_updates[-1])
            else:
                dev_batt[dev] = '-'

    return  dev_batt

def network_energy_summary(network_energy):
    ret = dict()
    for net_tuple in network_energy:
        ret[net_tuple[0]] = float(net_tuple[1])

    return ret


'''parse an structure simlog data and after that
creates a stats summary for each node of the simulation
returns a Pandas.DataFrame where rows are devices and columns
are summarized quantities of job states, battery information among others'''
def build_nodes_stats_summary(logFilePath):
    logparts = load_log_data(logFilePath)

    df = pd.DataFrame.from_dict(count_jobs(logparts[DEVICES_ID],logparts[JOB_ASSIGNED]),orient='index', columns=['assigned'])
    df['received'] = pd.Series(count_jobs(logparts[DEVICES_ID], logparts[JOB_INPUT_TRANSF_COMPLETED]))
    df['finished'] = pd.Series(count_jobs(logparts[DEVICES_ID],logparts[JOB_FINISHED]))
    df['completed'] = pd.Series(count_jobs(logparts[DEVICES_ID],logparts[JOB_COMPLETED]))
    df['unfinished'] = pd.Series(count_jobs_diff(logparts[DEVICES_ID],logparts[JOB_ASSIGNED],logparts[JOB_COMPLETED]))
    df['init_batt'] = pd.Series(device_init_batt(logparts))
    df['batt_after_last__job'] = pd.Series(battlevel_after_last_job(logparts[DEVICES_ID],logparts[JOB_COMPLETED],logparts[DEV_LEFT],logparts[BATT_UPDATE]))
    df['net_energy(%)'] = pd.Series(network_energy_summary(logparts[NETWORK_ENERGY]))

    return df

def load_devices(logFileName):
    devices = dict()
    for line in open(logFileName).readlines():
        if re.search(';Device started$', line):
            deviceId = line.split(';')[1]
            devices[deviceId] = DEFAULT_BATTERY_LEVEL
    return devices

def battery_deviation(devices):
    return statistics.stdev(devices.values())

def build_progress(logFileName, devices):
    jobs_finished_in_window = []
    stdev_in_window = []
    gaps = []
    jobs_finished = 0
    preprocessed_lines = []
    for line in open(logFileName).readlines():
        if re.match('^[0-9]+;', line):
            curSimTime = line.split(';')[0]
            if re.search(JOB_FINISHED, line):
                preprocessed_lines.append((curSimTime,'jobFinished'))
            if re.search(BATT_UPDATE, line):
                deviceId = line.split(';')[2].split()[5]
                batteryLevel = line.split(';')[2].split()[6].split('=')[1]
                preprocessed_lines.append((curSimTime, 'batteryUpdate', deviceId, batteryLevel))

    lastTime = float(preprocessed_lines[-1][0])
    multiplier = 1
    while lastTime > multiplier * WINDOW_SIZE:
        multiplier += 1
    print('Adding adjustment entry, lastSimTime = ' + str(lastTime) + ', lastAddedTime = ' + str(multiplier * WINDOW_SIZE))
    preprocessed_lines.append((multiplier * WINDOW_SIZE,'fakeEvent'))
    # Fin fix

    currWSize = WINDOW_SIZE
    index = 0
    total = 0
    while index < len(preprocessed_lines):
        tuple = preprocessed_lines[index]
        tupleTime = float(tuple[0])
        if (index + 1 < len(preprocessed_lines)) and (float(preprocessed_lines[index+1][0]) - tupleTime > WINDOW_SIZE):
            factor = math.floor((float(preprocessed_lines[index+1][0]) - tupleTime) / WINDOW_SIZE)
            gaps.append((tupleTime,factor))
            #deviation = battery_deviation(devices)
            #for i in range(0, factor):
            #    jobs_finished_in_window.append(0)
            #    stdev_in_window.append(deviation)
        if tupleTime >= currWSize:
            jobs_finished_in_window.append(jobs_finished)
            jobs_finished = 0
            stdev_in_window.append(battery_deviation(devices))
            currWSize += WINDOW_SIZE

        # si es job finished, acumular en la ventana
        if tuple[1] == 'jobFinished':
            jobs_finished += 1
            total += 1
        # si es battery event, actualizar en dict()
        if tuple[1] == 'batteryUpdate':
            devices[tuple[2]] = float(tuple[3])
        if tuple[1] == 'fakeEvent':
            pass

        index+=1

    return jobs_finished_in_window, stdev_in_window, gaps


'''if (len(sys.argv) < 2):
    print("Please specify a log file name")
    exit(-1)
logFileName = sys.argv[1]
print(build_nodes_stats_summary(logFileName).to_string())
'''

# input_dir: dirpath relative to ../results/ (avoid first slash '/', and spaces in the path).

usersubdir = (sys.argv[1] if len(sys.argv) > 1 else "")
input_dir = "./logoutTesis/" + usersubdir + "/"

output_dir = input_dir

for subdir, dirs, files in os.walk(input_dir):
    fig, (ax1, ax2) = pylab.subplots(2, sharex=True, sharey=False, figsize=(16, 10), dpi=260)
    colors = itertools.cycle(['b', 'g', 'r', 'c', 'm', 'y', 'k', 'w'])
    writer = pd.ExcelWriter(input_dir+'nodesStats.xlsx', engine='xlsxwriter')
    for logFileName in files:
        if logFileName.endswith(".log"):
            logFilePath = input_dir + logFileName
            simname, extension = os.path.splitext(logFileName)
            print("calculating metrics for: " + simname)

            nodes_info = build_nodes_stats_summary(logFilePath)
            #nodes_info.to_csv(input_dir+simname + ".csv")
            nodes_info.to_excel(writer, sheet_name=simname[5:35])

            devices = load_devices(logFilePath)
            print("Devices count:" + str(len(devices)))

            jobs, deviations, gaps = build_progress(logFilePath, devices)

            # Busco empezando del final. Mientras sea cero la cuenta de jobs en la ventana, decremento
            # El indice que queda representa la ultima ventana de tiempo donde se ejecuto algo
            lastNonZero=len(jobs)-1
            while (lastNonZero >= 0):
                if jobs[lastNonZero] > 0:
                    break
                lastNonZero=lastNonZero-1

            if lastNonZero < 0:
                print("No job was executed!")
                exit(0)

            print(jobs[0:lastNonZero+1])
            print(deviations[0:lastNonZero+1])
            print("Executed jobs: " + str(reduce((lambda x, y: x + y), jobs)))
            print("Gaps found: " + str(len(gaps)))
            color = next(colors)
            ax1.plot([item for item in range(0, lastNonZero+1)], jobs[0:lastNonZero + 1], color, label=simname)
            #ax2.plot([item for item in range(0, lastNonZero+1)], [int(yvalue)/100000 for yvalue in deviations[0:lastNonZero + 1]], color)
            ax2.plot([item for item in range(0, lastNonZero+1)], [int(yvalue)/100000 for yvalue in deviations[0:lastNonZero + 1]], color)

    fig.legend(loc='best')
    ax1.grid()
    ax1.set_ylabel('No. of executed jobs')
    ax2.grid()
    ax2.set_ylabel('Energy consumed deviations in the pool of devices')
    #pylab.suptitle("Metrics for "+logFileName[:-4])

    pylab.savefig(input_dir+"schedulersComparison.png", format='png')
    writer.save()
            
