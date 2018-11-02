import time

import stomp


class MyListener(stomp.ConnectionListener):
    def on_error(self, headers, message):
        print('received an error "%s"' % message)
    def on_message(self, headers, message):
        print('received a message "%s"' % message)
        print('received a headers "%s"' % headers)
        # Send message again as it did not managed to be sent
        # conn1.send(body=message, destination='beans.queue.eyetrackerQueue')

# digital ocean server 1
# conn1 = stomp.Connection([('142.93.109.50',61613)])
# localhost

# EYETRACKER
# FeatureExtractionSErver
conn1 = stomp.Connection([('142.93.109.50',61613)])
# CassandraDB
# conn1 = stomp.Connection([('104.248.35.208',61613)])

conn1.set_listener('', MyListener())
conn1.start()
conn1.connect('user', 'user', wait=True)
conn1.subscribe(destination='beans.queue.testQueue', id=1, ack='auto')

# # SHIMMER
# conn2 = stomp.Connection()
# conn2.set_listener('', MyListener())
# conn2.start()
# conn2.connect('user', 'user', wait=True)
# conn2.subscribe(destination='beans.queue.shimmerQueue', id=2, ack='auto')

# text = open('EyeShimmerLSL/gazedataLSL-2018-10-04-142408.csv', 'r').read()
f1 = open('EyeShimmerLSL/testEye.csv', 'r').read()
encoded1 = f1.encode('utf-8')
# f2 = open('EyeShimmerLSL/testShimmer.csv', 'r').read()
# encoded2 = f2.encode('utf-8')
n=0
conn1.send(body=f1, destination='beans.queue.testQueue')

# while (n < 1):
# 	f = str(n)
# 	fin = f.encode('utf-8')
# 	conn1.send(body=f1, destination='beans.queue.eyetrackerQueue')
# 	time.sleep(1)
# 	n=n+1
# conn2.send(body=f2, destination='beans.queue.shimmerQueue')
time.sleep(2)
conn1.disconnect()
# conn2.disconnect()
