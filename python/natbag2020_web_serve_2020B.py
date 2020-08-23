#!/usr/bin/python




import subprocess




from flask import Flask, request

app = Flask(__name__)
COMPILATION_DIR = '/Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src'

RUNNABLE_DIRECTORY = "/Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/bin"


terminalCompile = '-d build -cp ./jars/*.jar *.java'
terminalRun = 'java -cp ./build:./jars/AirportDev14July.jar AirportActivition'


proc = subprocess.Popen(terminalCompile,shell=True,cwd=COMPILATION_DIR)

#subprocess.check_call()
# // Args:
# 	// 0 - User Interface kind
# 	// 1 - departures or Arrivials
# 	// 2 - airline brand
# 	// 3 - country
# 	// 4 - City
# 	// 5 - Airport
# 	// 6 - starting date
# 	// 7 - ending date
# 	// 8 - week days [ 1= Sunday , 2=Monday... 7=Saturday]


@app.route("/check")
def printCheck():
    # print("hello") # not working
    return 'hello'


     # localhost:8000/departures?outFormat=html&airline=El-Al&country=USA&weekDays=127
@app.route("/departures")
def dep():
    return subprocess.check_output(["java",
                                    "boardActivition/AirportActivation",
                                    request.args.get('outFormat',''), "departures", # args[0] + args[1]
                                    request.args.get('airline',''), request.args.get('country',''),# args[2] + args[3]
                                    request.args.get('city',''), request.args.get('airport',''),# args[4] + args[5]
                                    request.args.get('startingDate',''), request.args.get('endingDate',''),# args[6] + args[7]
                                    request.args.get('weekDays','')],# args[8]
                                    cwd=RUNNABLE_DIRECTORY)
#                                     request.args.get('day1'), request.args.get('month1'),
#                                     request.args.get('year1'), request.args.get('day2'),
#                                     request.args.get('month2'), request.args.get('year2'),
#                                     request.args.get('sunday'), request.args.get('monday'),
#                                     request.args.get('tuesday'), request.args.get('wednesday'),
#                                     request.args.get('thursday'), request.args.get('friday'),
#
#                                     request.args.get('saturday')])


@app.route("/arrivals")
def arr():
    return subprocess.check_output(["java",
                                    "boardActivition/AirportActivation",
                                    request.args.get('outFormat', ''), "arrivals",  # args[0] + args[1]
                                    request.args.get('airline', ''), request.args.get('country', ''),
                                    # args[2] + args[3]
                                    request.args.get('city', ''), request.args.get('airport', ''),  # args[4] + args[5]
                                    request.args.get('startingDate', ''), request.args.get('endingDate', ''),
                                    # args[6] + args[7]
                                    request.args.get('weekDays', '')],  # args[8]
                                   cwd=RUNNABLE_DIRECTORY)


app.run(port=8000, host="0.0.0.0")



#Stages to run this code in the terminal:

# a. create directories for JARS and Builds (mkdir JARS , mkdir build)
# b. go to eclipse & configure classpath for project, set main as the Board (not Program)
# c. export the project as jar file to the JARS folder we made in a
# d. (for future using of the code) configure classpath back to Program.
##
#1. build path with the jar files in place (in order to detect dependencis and libraries etc )  for compilatio
#this stage will build alot of files in the "build" folder
#CODE:      javac -d build -cp ./jars/*.jar *.java
#javaC - the C stands for compilation of the java code.
# the -d do???

#2. run the
#CODE:      java -cp ./build:./jars/AirportDev14July.jar AirPortBoard
# running the AirPortBoard class using the AirportDev14July.jar file to know dependencies


