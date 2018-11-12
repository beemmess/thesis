from eyetracker import *

f = """6682.276668686,0.061483658850193024,0.684330403804779,0.03360903263092041,0.6470507383346558,3.0,2.5
6682.288781205,0.06113949790596962,0.6873190402984619,0.032140102237463,0.6550177335739136,3.1,2.2
6682.299485908,0.07175391167402267,0.6920405030250549,0.03520631417632103,0.6568324565887451,2.9,2.8
6682.310080276,0.07175391167402267,0.6920405030250549,0.03566160053014755,0.6553481817245483,2.6,2.2
6682.321882406,0.07267249375581741,0.7007426023483276,0.02926032431423664,0.6657865047454834,2.1,2.5
6682.332974192,0.06803920865058899,0.7047094106674194,0.03180355578660965,0.6690114140510559,2.76,2.3
6682.34317745,0.06803920865058899,0.7047094106674194,0.033693376928567886,0.6698177456855774,2.0,2.42
6682.355092609,0.06672101467847824,0.7006564736366272,0.037388790398836136,0.657270610332489,2.3,2.8
6682.366171844,0.06873149424791336,0.6877057552337646,0.036392077803611755,0.6500191688537598,3.9,4.2
6682.366292609,0.06672101467847824,0.7006564736366272,0.037388790398836136,0.657270610332489,2.3,2.8
6682.36631844,nan,nan,0.036392077803611755,0.6500191688537598,3.9,4.2
6682.376653194,nan,0.6877057552337646,0.039480891078710556,nan,2.9,5.2
6682.377631844,nan,nan,nan,nan,3.9,4.2
6682.3778653194,nan,0.6877057552337646,0.039480891078710556,nan,2.9,5.2
6682.378631844,0.06672101467847824,0.7006564736366272,nan,nan,nan,4.2
6682.3798653194,nan,0.6877057552337646,0.039480891078710556,nan,2.9,nan
6682.3798653194,nan,0.6877057552337646,nan,0.6877057552337646,2.9,2.9
6682.388687789,0.06889772415161133,0.685947597026825,0.03873371705412865,0.6584392786026001,2.56,2.14"""
print(f)

# file = open('testEye.csv', 'r').read()
json_input = { "userId": "Cucumber", "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR", "data": f}
string = Clean.substitution(json_input)
print(string["data"])

# print(string)
