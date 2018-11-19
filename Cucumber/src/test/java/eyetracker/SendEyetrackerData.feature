# Created by beemmess at 02/11/2018
Feature: Send a raw Eyetracking data to Feature extraction server


  Scenario: As a user I want my eyetracking data to be successfully sent to the server
    Given That the eyetracking data of the user is collected into "eyetracker_data.json"
    When The raw data is sent to the server "http://142.93.109.50:8080/FeatureExtractionServer/api/eyetracker"
    Then The raw data is succesfully sent to the server and respond code is <200>

  Scenario: As a user, I try to send corrupted eyetracking data to the server
    Given That the eyetracking data of the user is collected into "eyetracker_data_corrupted.json"
    When The raw data is sent to the server "http://142.93.109.50:8080/FeatureExtractionServer/api/eyetracker"
    Then The raw data is unsuccesfully sent to the server and respond code is <400>


#    Scenario: As a researcher, I want to preprocess the data that has been collected
#      Given That the eyetracking data of the user is collected into "eyetracker_data_before_cleaning.json"
#      When The raw data is sent through the cleaning process
#      Then I the data should look like this after cleaning "eyetracker_data_after_cleaning.json"