#Importing of libraries used within this project
import tweepy
from textblob import TextBlob
import pandas as pd
import re
import random
import numpy as np
import sched, time
from time import sleep


#Twitter Authentication tools
auth = tweepy.OAuthHandler("99KvMnx9eBBCIPFdq9hYwe0aw", "JXDeGG1hSegQ6U8ZSqw7HAwV2fgHBRD00upmEEe2pxNXmXVAJS")
auth.set_access_token("1361053266285625345-nX4PlzjOFziq4Ybd5IrH6mD3n2pCc8", "yM4sSKJ7vQ0Qp1JOJqHtOYzjqz1iCTmboYWOEq1kw2uai")

api = tweepy.API(auth)

FILE_NAME = 'last_seen.txt'
neutralFile = 'neutral.txt'
positiveFile = 'positive.txt'
negativeFile = 'negative.txt'

def read_last_seen(FILE_NAME):
    file_read = open(FILE_NAME, 'r')
    last_seen_id = int(file_read.read().strip())
    file_read.close()
    return last_seen_id

def store_last_seen(FILE_NAME, last_seen_id):
    file_write = open(FILE_NAME, 'w')
    file_write.write(str(last_seen_id))
    file_write.close()
    return

neutral = open("neutral.txt", "r")
neutralDat = neutral.readlines()

positive = open("positive.txt", "r")
positiveDat = positive.readlines()

negative = open("negative.txt", "r")
negativeDat = negative.readlines()

tweet = open("botTweets.txt", "r")
tweetData = tweet.readlines()

schedtweet = sched.scheduler(time.time, time.sleep)

#while True:

mentions = api.mentions_timeline(read_last_seen(FILE_NAME), lang='en', tweet_mode='extended')

if(len(mentions) > 0):
    #show recent mentions 
    print('Show the most recent mentions \n')
    i = 1
    for tweet in reversed(mentions):
        print(str(i) + ':' + str(tweet.id) + '-' + tweet.full_text + '\n')
        i = i + 1
        store_last_seen(FILE_NAME, tweet.id)

    #C a dataframe with a column called Tweets
    df = pd.DataFrame( [tweet.full_text for tweet in mentions], columns=['Tweets'])

    #show the rows
    df.head()

    #remove urls etc

    def cleanTweets(text):
        text = re.sub(r'@[A-Za-z0-9]+', '', text)
        text = re.sub(r'#', '', text)
        text = re.sub(r'RT[\s]+', '', text)
        text = re.sub(r'https?:\/\/\S+', '', text)
        return text

    df['Tweets'] = df['Tweets'].apply(cleanTweets)

    df

    def getSubjectivity(text):
        return TextBlob(text).sentiment.subjectivity

    def getPolarity(text):
        return TextBlob(text).sentiment.polarity

    df['Subjectivity'] = df['Tweets'].apply(getSubjectivity)
    df['Polarity'] = df['Tweets'].apply(getPolarity)

    df

    def getAnalysis(score):
        if score < 0:
            j = 0
            while j < len(negativeDat):
                api.update_status('@' + tweet.user.screen_name + negativeDat[j], tweet.id)
                j = j + 1
                return 'Negative'
        elif score == 0:
            z = 0
            while z < len(neutralDat):
                # api.update_status('@' + tweet.user.screen_name + random.choice(neutralDat), tweet.id)
                api.update_status('@' + tweet.user.screen_name + neutralDat[z], tweet.id)
                z = z + 1
                return 'Neutral'
        else:
            e = 0
            while e < len(positiveDat):
                #  api.update_status('@' + tweet.user.screen_name + random.choice(positiveDat), tweet.id)
                api.update_status('@' + tweet.user.screen_name + positiveDat[e], tweet.id)
                e = e + 1
                return 'Positive'

    df['Analysis'] = df['Polarity'].apply(getAnalysis)

    df
    sleep(60)


#        sleep(20)

#def main():
   # api = create_api()
#  while True:
     #   checkmentions()
     #   time.sleep(20)
def tweetTimer(loops):
    r = 0
    while r < len(tweetData):
        api.update_status(tweetData[r])
        r = r + 1
        schedtweet.enter(1800, 1, tweetTimer, (loops,))

schedtweet.enter(1800, 1, tweetTimer, (schedtweet,))
schedtweet.run()