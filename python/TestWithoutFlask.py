import subprocess


def main():
    print('Reached main')
   #  setDir = '/Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src'
    # terminalCompile = '-d build -cp ./jars/*.jar *.java'
    # terminalRun = 'java -cp ./build:./jars/AirportDev14July.jar AirportActivition'

    # proc = subprocess.Popen(terminalCompile, shell=True, cwd=setDir)
    print('be4 Sub Process')

    subprocess.check_output(["java", "-cp",
                             "/Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/bin/",
                             "boardActivition/AirportActivation",
                             'html', "departures",  # args[0] + args[1]
                             'Arkia', "",  # args[2] + args[3]
                             '', '',
                             '', ''])  # args[4] + args[5]
    print('passed Sub Process')
    print(s)

    #/Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/bin/boardActivition
    # / Users / SRazStudent / git / Airport2020B_JAVA / AirportDevTools2020B / bin / boardActivition


#                                   ["java", "-classpath",
#                                     "/home/afeka/workspace/g1/bin", "NatbagMain",


if __name__ == "__main__":
    main()
