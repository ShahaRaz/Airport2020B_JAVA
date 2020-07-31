#!/usr/bin/python




import subprocess





from flask import Flask, request

app = Flask(__name__)
setDir = '/Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src'
terminalCompile = '-d build -cp ./jars/*.jar *.java'
terminalRun = 'java -cp ./build:./jars/AirportDev14July.jar AirportActivition'
#proc = subprocess.Popen(terminalCompile,terminalRun,shell=True,cwd = setDir)

#_______________________________________________________________DO THAT! TODO ______________
#AFTER COMPILING IS DONE! : RUN LIKE THIS !! 27July2020
# java -classpath /Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/bin/ boardActivition/AirportActivation
# "console"  "departuers"   ""  "Spain USA India"  ""  ""  "23/04/2020"  "28/11/2020"  "67"
#________________________________________________________________DO THAT! TODO ______________


proc = subprocess.Popen(terminalCompile,shell=True,cwd=setDir)
#proc = subprocess.Popen(terminalRun,shell=True,cwd=setDir)

#
# proc = subprocess.run('cd..')
# proc = subprocess.run('ls'
proc = subprocess.Popen("ls" , cwd = setDir)
# proc = subprocess.run('ls')
# proc = subprocess.Popen(terminalCompile,shell=True)
# proc = subprocess.Popen(terminalRun,shell=True)
# subprocess.run(["javaב", "-d",
#                                 "Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src", " -d build -cp ./jars/*.jar *.java"])
#
# subprocess.run(["java", "-classpath",
#                                 "Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src", "  java -cp ./build:./jars/AirportDev14July.jar AirPortBoard"])

# "html"  "departures"   "Arkia"  "USA"  "New-York"  "JFK"  "23/04/2020"  "01/05/2020"  "435"
# "html" , "departures" , "Arkia" , "" , "" , "" , "" , "" , "" , ""
# 	1       2                     3        4.    5.  6.  7     8   9
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


@app.route("/check2")
def printCheck2():
  #  print("hello") # not working
    return '1'+'2'


# localhost:8000/departures?outFormat=html&airline=El-Al&country=USA
@app.route("/departures")
def dep():
    return subprocess.check_output(["java",  "-classpath",
                                    "/Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/bin/ boardActivition",
                                    "AirportActivation",
                                    request.args.get('outFormat'), "departures", # args[0] + args[1]
                                    request.args.get('airline'), request.args.get('country'),# args[2] + args[3]
                                    request.args.get('city'), request.args.get('airport')])# args[4] + args[5]
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
    return subprocess.check_output(["java", "-classpath",
                                    "/Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src/bin", "AirportActivition",
                                    request.args.get('outformat'), "arrivals",# args[0] + args[1]
                                    request.args.get('airline'), request.args.get('country'),# args[2] + args[3]
                                    request.args.get('city'), request.args.get('airport'),# args[4] + args[5]
                                    request.args.get('day1'), request.args.get('month1'),
                                    request.args.get('year1'), request.args.get('day2'),
                                    request.args.get('month2'), request.args.get('year2'),
                                    request.args.get('sunday'), request.args.get('monday'),
                                    request.args.get('tuesday'), request.args.get('wednesday'),
                                    request.args.get('thursday'), request.args.get('friday'),
                                    request.args.get('saturday')])


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


#-classpath bin  /Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src/Program.java


# "/Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src/bin", "NatbagMain",
# # old Copy
# import subprocess
#
# from flask import Flask, request
#
# app = Flask("natbag2020_app")
#
# @app.route("/departures")
# def dep():
#     return subprocess.check_output(["java", "-classpath",
#                                     "/home/afeka/workspace/g1/bin", "NatbagMain",
#                                     request.args.get('outformat'), "departures",
#                                     request.args.get('airline'), request.args.get('country'),
#                                     request.args.get('city'), request.args.get('airport'),
#                                     request.args.get('day1'), request.args.get('month1'),
#                                     request.args.get('year1'), request.args.get('day2'),
#                                     request.args.get('month2'), request.args.get('year2'),
#                                     request.args.get('sunday'), request.args.get('monday'),
#                                     request.args.get('tuesday'), request.args.get('wednesday'),
#                                     request.args.get('thursday'), request.args.get('friday'),
#                                     request.args.get('saturday')])
# #-classpath bin  /Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src/Program.java
# @app.route("/arrivals")
# def arr():
#     return subprocess.check_output(["java", "-classpath",
#                                     "/home/afeka/workspace/g1/bin", "NatbagMain",
#                                     request.args.get('outformat'), "arrivals",
#                                     request.args.get('airline'), request.args.get('country'),
#                                     request.args.get('city'), request.args.get('airport'),
#                                     request.args.get('day1'), request.args.get('month1'),
#                                     request.args.get('year1'), request.args.get('day2'),
#                                     request.args.get('month2'), request.args.get('year2'),
#                                     request.args.get('sunday'), request.args.get('monday'),
#                                     request.args.get('tuesday'), request.args.get('wednesday'),
#                                     request.args.get('thursday'), request.args.get('friday'),
#                                     request.args.get('saturday')])
#
# app.run(port=8000, host="0.0.0.0")

#java -cp ./build:./jars/AirportDev20July.jar boardActivition/AirportActivition
# "console"  "departuers"   ""  "Spain USA India"  ""  ""  "23/04/2020"  "28/11/2020"  "67"

#cd
#java -classpath . boardActivition/AirportActivation "console"  "departuers"   ""  "Spain USA India"  ""  ""  "23/04/2020"  "28/11/2020"  "67"

# java -classpath /Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/bin/ boardActivition/AirportActivation
# "console"  "departuers"   ""  "Spain USA India"  ""  ""  "23/04/2020"  "28/11/2020"  "67"