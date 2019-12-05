#!/usr/bin/python
import json, requests, subprocess

ELASTICSEARCH="http://localhost:9200/_cat/indices?format=json&pretty&s=index:asc"

def Cleanup():
 # Request data from link as 'str'
 data = requests.get(ELASTICSEARCH).text
 # convert 'str' to Json
 data = json.loads(data)
 for i in data:
     if(i['index'].startswith("elastiflow-")):
        requests.delete("http://localhost:9200/" + i['index'])
        break
 check_once()

def check_once():
 threshold = 90
 partition = "/"
 df = subprocess.Popen(["df","-h"], stdout=subprocess.PIPE)
 for line in df.stdout:
    splitline = line.decode().split()
    if splitline[5] == partition:
        if int(splitline[4][:-1]) > threshold:
            Cleanup()


check_once()