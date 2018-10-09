from sklearn.decomposition import PCA
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


data = pd.read_csv("gazedataLSL/gazedataLSL2018-10-01-120329.csv")

features =['leftX','leftY','rightX','rightY']

X = data.loc[:, features].values
y = data.loc[:,['timestamp']].values

pca = PCA(n_components=2)

pca_samples = pca.fit_transform(X)

principalDf = pd.DataFrame(data = pca_samples, columns = ['pca 1', 'pca 2'])
# Generate PCA results plot
findalDf = pd.concat([principalDf, data[['timestamp']]], axis = 1)


fig = plt.figure(figsize = (8,8))
ax = fig.add_subplot(1,1,1) 
ax.set_xlabel('Principal Component 1', fontsize = 15)
ax.set_ylabel('Principal Component 2', fontsize = 15)
ax.set_title('2 component PCA', fontsize = 20)
targets = finalDf['timestamp']
colors = ['r', 'g', 'b']
for target in targets:
    indicesToKeep = finalDf['target'] == target
    ax.scatter(finalDf.loc[indicesToKeep, 'principal component 1']
               , finalDf.loc[indicesToKeep, 'principal component 2']
               , s = 50)
ax.legend(targets)
ax.grid()