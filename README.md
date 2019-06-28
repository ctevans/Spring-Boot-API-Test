
# KNOWN ISSUES.

Unfortunately there is a known issue with the unit tests attempting to test validation on 
the API Endpoints with Request Bodies with missing information. This error results in "200OK" being
returned for all of the unit tests, which is an unexpected result and can be verified to be incorrect
using Postman.

# Why this issue remains

Stackoverflow had the ability to create new questions down and so there were no resources 
I could utilize effectively to solve this problem in the 24 hour time period set. Unfortunately
I couldn't find any resources to help me tackle the problem in time :(

Source: https://meta.stackoverflow.com/questions/386581/cant-post-a-new-question-using-guided-mode
