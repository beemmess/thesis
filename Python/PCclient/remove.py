import os
from glob import glob


# if os.path.exists("buffer/gazedata-2019-01-08-122714.csv"):
# 	os.remove("buffer/gazedata-2019-01-08-122714.csv")

for file in glob("buffer/gazedata*.csv"):
	os.remove(file)
