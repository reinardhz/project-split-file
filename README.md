### Split Application

The command line "split" program is too slow to split a large file, because it is only use 1 thread.
Splitting a 1.4-Gigabytes video into 52 Megabytes each, will take forever.

That is why I make this application. This split application is a java application that can split a large file using multithread.
Splitting a 1.4-Gigabytes video into 52 Megabytes each is just a few seconds.

### How to use

First of all, make sure that you have java 8 installed in your computer. To make sure java is installed properly is to open terminal or command line application
then execute:

```
java -version
```

If java is installed properly, it will display output like:

```
java version "1.8.0_131"
Java(TM) SE Runtime Environment (build 1.8.0_131-b11)
```

Secondly, there are two options to use the application, first option is to just download the jar file.
Second option is to build this project, and run the generated jar.

#### Download

Download `split-1.0.jar` at 

#### Build
1.First git clone this project.

2.Go to the directory of the project:
```
cd project-split-folder
```

3.For user of unix-like operating system including macos, execute:
```
./gradlew clean build
```

4.For user of windows operating system, execute:
```
gradlew.bat clean build
```

5.After building the project, you should find the jar file (split-1.0.jar) in `project-split-folder/build/libs` directory.

#### Usage

```
java -Xmx[memory_in_mega_bytes]M -jar split-1.0.jar -b [bytes] -t [number_of_threads] [file_path]
```

Lets say you have a file named "movie.mp4" and its size is 1.4 Gigabytes, and you want to split the file to 52 Megabytes each.
Then, below command will do it:

```
java -Xmx2500M -jar split-1.0.jar -b 52428800 -t 4 "./movie.mp4"
```

It will use maximum runtime heap memory of 2.5 GB and 4 threads to split "movie.mp4" file into 52428800 bytes (52.4 MB) of each file. 
The splitted part files will be generated in current directory:

```
filename=part1, size=52428800 bytes
filename=part2, size=52428800 bytes
filename=part3, size=52428800 bytes
filename=part4, size=52428800 bytes
filename=part5, size=52428800 bytes
filename=part6, size=52428800 bytes
filename=part7, size=52428800 bytes
filename=part8, size=52428800 bytes
filename=part9, size=52428800 bytes
filename=part10, size=52428800 bytes
filename=part11, size=52428800 bytes
filename=part12, size=52428800 bytes
filename=part13, size=52428800 bytes
filename=part14, size=52428800 bytes
filename=part15, size=52428800 bytes
filename=part16, size=52428800 bytes
filename=part17, size=52428800 bytes
filename=part18, size=52428800 bytes
filename=part19, size=52428800 bytes
filename=part20, size=52428800 bytes
filename=part21, size=52428800 bytes
filename=part22, size=52428800 bytes
filename=part23, size=52428800 bytes
filename=part24, size=52428800 bytes
filename=part25, size=52428800 bytes
filename=part26, size=52428800 bytes
filename=part27, size=52428800 bytes
filename=part28, size=15068608 bytes
```

Note: Because this program will read all bytes from the file, then you should set the java runtime memory (heap memory), above the size file that you want to split.
