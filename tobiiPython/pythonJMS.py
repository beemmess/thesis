import time
import sys

import stomp

class MyListener(stomp.ConnectionListener):
    def on_error(self, headers, message):
        print('received an error "%s"' % message)
    def on_message(self, headers, message):
        print('received a message "%s"' % message)
conn = stomp.Connection()
conn.set_listener('', MyListener())
conn.start()
conn.connect('user', 'user', wait=True)

conn.subscribe(destination='jms.queue.testQueue', id=1, ack='auto')

# text = open('EyeShimmerLSL/gazedataLSL-2018-10-04-142408.csv', 'r').read()
f = open('EyeShimmerLSL/gazedataLSL-2018-10-04-142408.csv', 'r').read()
encoded = f.encode('utf-8')
conn.send(body=encoded, destination='jms.queue.testQueue')
time.sleep(2)
conn.disconnect()
