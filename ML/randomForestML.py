# Import libraries necessary for this project
import numpy as np
import pandas as pd

# Load the Census dataset
df = pd.read_csv("ML/movie_metadata.csv")

df = df[df.country == 'USA']
df = df[df.language == 'English']
df = df[df.color == 'Color']
df = df[df.duration > 79]

# Remove all rows with that contains missing data
df = df.dropna()


# To begin with, we have 28 colums of data, a small description of that data can seen here below.
# 
# * **color**: If the movie is in color or not
# * **director_name**: Name of the directore of the movie
# * **num_critic_for_reviews**: Number of critics for the movie
# * **duration**: Duration of the movie in minutes
# * **director_facebook_likes**: Number of facebook likes that the director has
# * **actor_3_facebook_likes**: Number of facebook likes that the movie actor nr 3 has
# * **actor_2_name**: Name of movie actor nr 2
# * **actor_1_facebook_likes**: Number of facebook likes that the movie actor nr 1 has
# * **gross**: Gross revenu of the movie in USD
# * **genres**: Types of genre that the movie has
# * **actor_1_name**: Name of movie actor nr 1
# * **movie_title**: Title of the movie
# * **num_voted_users**: Number of votes that the movie has
# * **cast_total_facebook_likes**: Total number of facebook likes that the cast has 
# * **actor_3_name**: Name of movie actore nr 3
# * **facenumber_in_poster**: What actors are on the poster (displayed as the number of the actor 1, 2 or 3)
# * **plot_keywords**: Plot keywords, several words descriping the plot i.e. avatar|future|marine|native|paraplegic
# * **movie_imdb_link**: IMDb link to the movie
# * **num_user_for_reviews**: Number of users that wrote a review about the movie
# * **language**: Language of the movie
# * **country**: Country of the movie
# * **content_rating**: What the ratings is, i.e PG-13
# * **budget**: Budget that the movie had for production
# * **title_year**: Year that the movie was released
# * **actor_2_facebook_likes**: Number of facebook likes that the movie actor nr 2 has 
# * **imdb_score**: Movie score
# * **aspect_ratio**: The aspect ratio of an image describes the proportional relationship between its width and its height
# * **movie_facebook_likes**: Number of facebook likes the movie has
#  
# Few of these are irrelevant to us, such as 'movie_imdb_link' and 'country' (as we are only looking at USA) etc. These colums will be removed later on.


# Extract all genres and store in the rows, comma seperated
all_rows_genre = df['genres'].str.rsplit(pat='|')

# Take all the rows and create a list of lists of all the plot key words
listOfList = []
for row in all_rows_genre:
    listOfList.append(row)

    
# Create a single list of all the words to extract only the unique words
flat_list = []
sublist = []
for n in range(len(listOfList)):
    sublist = listOfList[n]
    for j in sublist:
        flat_list.append(j)
        
# use set to extract unique words
uniqueSet = set(flat_list)
#  convert set to list
genres = list(uniqueSet)

# list of genres found in the data
# print(genres)

for genre in genres:
    df[genre] = df['genres'].str.contains(genre, regex=True).astype(int)

############
    
# Extract all content ratings and store in the rows, comma seperated
all_rows_content_rating = df['content_rating'].str.rsplit(pat='|')

# Take all the rows and create a list of lists of all the plot key words
listOfList = []
for row in all_rows_content_rating:
    listOfList.append(row)

    
# Create a single list of all the words to extract only the unique words
flat_list = []
sublist = []
for n in range(len(listOfList)):
    sublist = listOfList[n]
    for j in sublist:
        flat_list.append(j)
        
# use set to extract unique words
uniqueSet = set(flat_list)
#  convert set to list
content_ratings = list(uniqueSet)

#list of content ratings found in the data
# print(content_ratings)

#Next up is to put boolean in the specific content rating column if found
for content_rating in content_ratings:
    content_rating_name = 'content_rating{}'.format(content_rating)
    df[content_rating] = df['content_rating'].str.contains('^{}$'.format(content_rating), regex=True).astype(int)


# Now lets take a look at the data again. After a little bit of clean up, we end up with 2846 movies, 

# In[413]:

text_file = open("results.txt", "w")

# Total number of records
n_records = df.shape[0]

# Average score of the movies
average_score = np.sum(df['imdb_score'])/n_records

# Lowest score of movie
lowest_score = np.min(df['imdb_score'])

# Highest score of movie
highest_score = np.max(df['imdb_score'])


# Print the results
print("Total number of movies: {}".format(n_records))
text_file.write("Total number of movies: {}\n".format(n_records))

print("Highest movie score: {}".format(highest_score))
text_file.write("Highest movie score: {}\n".format(highest_score))

print("Lowest movie score: {}".format(lowest_score))
text_file.write("Lowest movie score: {}\n".format(lowest_score))

print("Average score: {}".format(average_score))
text_file.write("Average score: {}\n".format(average_score))



# Drop columns as described
df.drop(['country', 'language', 'movie_imdb_link', 'movie_title', 'color', 'facenumber_in_poster', 'plot_keywords', 'genres', 'content_rating'], axis = 1, inplace = True)
# print(list(df.columns))


# ## Model implementation

# ## Encode
# First thing first is to encode the data so that it can be used in the classifiers later on. [LabelEncoder](http://scikit-learn.org/stable/modules/preprocessing_targets.html) 
# is a utility class to help normalize labels such that they contain only values between 0 and n_classes-1. 


# from sklearn import preprocessing
from sklearn.preprocessing import LabelEncoder
def dfToEncode(df_enc, y):
#     https://www.kaggle.com/pratsiuk/valueerror-unknown-label-type-continuous
    columnsToEncode = list(df_enc.select_dtypes(include=['category','object']))
    lab_enc = LabelEncoder()
    for feature in columnsToEncode:
        try:
            df_enc[feature] = lab_enc.fit_transform(df_enc[feature])
        except:
            print('Something went wrong with feature: ' + feature)
    y_enc = lab_enc.fit_transform(y)
    return df_enc, y_enc



# Necasary imports
from sklearn.cross_validation import train_test_split
from sklearn.tree import DecisionTreeRegressor
from sklearn import preprocessing
from sklearn.preprocessing import MinMaxScaler
from sklearn import utils
from sklearn.metrics import accuracy_score
from sklearn.svm import SVC
from sklearn.svm import LinearSVC
from sklearn.linear_model import LogisticRegression
from sklearn import tree
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import r2_score
from sklearn.metrics import mean_squared_error
from math import sqrt
from sklearn.metrics import f1_score


# In[417]:




# A function that scales the numerical data with minMaxScaler, with the default values of (0,1)
def scaling(dataX):
    scaler = MinMaxScaler() # default=(0, 1)
    
    numerical = ['num_critic_for_reviews', 
                 'director_facebook_likes', 'actor_3_facebook_likes', 
                 'actor_1_facebook_likes', 'gross', 
                 'num_voted_users', 'cast_total_facebook_likes', 
                 'num_user_for_reviews', 'budget', 
                 'actor_2_facebook_likes', 
                 'movie_facebook_likes']
    
#     numerical = ['director_facebook_likes','actor_2_facebook_likes', 'actor_3_facebook_likes', 'actor_1_facebook_likes', 'cast_total_facebook_likes', 'budget','movie_facebook_likes']


    X = pd.DataFrame(data = dataX)
    X[numerical] = scaler.fit_transform(X[numerical])
    
    return X
    

def train_test(X,y):
    # Split the 'features' and 'imdb_score' data into training and testing sets
    X_train, X_test, y_train, y_test = train_test_split(X, 
                                                        y, 
                                                        test_size = 0.20, 
                                                        random_state = 10)
    ########## 

    # Show the results of the split
    print("Training/Testing set has {}/{} samples.".format(X_train.shape[0],X_test.shape[0]))
    text_file.write("Training/Testing set has {}/{} samples.\n".format(X_train.shape[0],X_test.shape[0]))
    print("------")
    text_file.write("------\n")

    
    randomForrestTuned(X_train, X_test, y_train, y_test)

    # Random forrest function
def randomForrestTuned(X_train, X_test, y_train, y_test):
    score_max = 0
    leaf_size_max =0
    sample_leaf_options = [1,2,3,4,5]
    for leaf_size in sample_leaf_options :
        RF_clf = RandomForestClassifier(n_estimators = 300, 
                                        criterion='gini',
                                        oob_score = False, 
                                        n_jobs = -1,
                                        random_state =10, 
                                        max_features = 'log2', 
                                        min_samples_leaf = leaf_size)
        RF_clf = RF_clf.fit(X_train, y_train)
        y_pred = RF_clf.predict(X_test)
        f1score = f1_score(y_test,y_pred, average='micro')

        if f1score> score_max:
            score_max = f1score
            leaf_size_max = leaf_size
    print("Random Forrest (Tuned Parameters) has the F1 score of: {}, with leaf size of: {}".format(score_max,leaf_size_max))
    text_file.write("Random Forrest (Tuned Parameters) has the F1 score of: {}, with leaf size of: {}\n".format(score_max,leaf_size_max))




# Run this code to generate score for all the movies in the subset.
df_subset = df
y = df_subset['imdb_score']
df_encoded, y_encoded = dfToEncode(df_subset,y)

X = df_encoded.drop(['imdb_score'], axis = 1)
# Call scaling function
X = scaling(X)

y = np.array(y_encoded)
# Round the number to an even number, if the movie has 
# 5.5 it rounds down to 5, if it has 5.6 it rounds up to 6
y = np.round(y/10)

print('Predicting movie score with all movies:')
text_file.write('Predicting movie score with all movies:\n')

train_test(X,y)

