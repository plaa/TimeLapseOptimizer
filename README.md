TimeLapseOptimizer
==================

A project to create a time lapse sequence from a continuous video.  The application attempts to select suitable frames from the video even if the video camera is oscillating back and forth.  It does this by selecting frames that are sufficiently similar to the previous frame.

This project is in a very early stage of development.


To run the app:

    mvn exec:java -Dexec.mainClass="com.github.timelapseoptimizer.App" -Dexec.args=myvideo.avi
