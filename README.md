# Transmission pass
The file shared architecture to provide stably file download.

The master server will distribution flow to other servent server.

Support mode:

1. Split file part to other server node. (Like the Hadoop)
2. Synchronized file to all computer, and accept command process distribution flow.
3. Streaming only server. (ReentrantLock by one download people)

# Description
The fast way you use LAN transmission file for large quantity user to download.

Before running transmission pass, you need one Master side to provide download and all file synchronize, or if you install transmission pass node-mode in other computer, you have to enter the ip to let you can use that computer become to download node.
