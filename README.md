# Commusify
Commusify - A music service truly connected to it's community.
## About
This was a school project at BBZW Sursee.

![Important]()**Video for my teacher**: https://bit.ly/3Q6AAzC

The task was to show that one can master object-oriented programming. I hope I was able to do that with this project as I personally love programming :). Commusify (= Community + Music) is a console application where users can log in and listen to music. But you can also pretend to be an artist and listen to tracks yourself. I have also taken over quite some logic from reality regarding this topic:
- Of course there are also edits in addition to tracks, I have implemented remixes and mashups for this. The difference is that a remix is an edit of a single track, and a mashup can include any number of edited tracks.
- I also have playlists, although there is something special about my implementation that there are also so-called playable lists. This is more or less an invention of mine. Playable lists can not only contain tracks, but also other playable lists. There are also normal playlists, but these can only contain individual tracks. This way, funnily enough, the normal album is a special case of the playable list, although it seems much more exotic.
- In addition to playlists, there are also albums. These are a special case of normal playlists, as they can only contain tracks. There are different album types: According to the site https://blog.landr.com/album-formats/ there are Singles (= 1 track), Extended Plays (a few tracks), Long Plays (some tracks) and Double Long Plays ( twice as many as in long play), all of which are limited album types as historically they were based on record discs. Only the mixtape is an album type with no limit in Commusify, but it has a description for it, otherwise it can be unclear what it is intended for, as it could literally contain like, for example, 10 but also 1000 tracks.
- An artist can have different members in Commusify, but still count as one artist. I was thinking of artists like "Swedish House Mafia" who are like exactly like that.
- A track always has a genre, which can also contain subgenres resp. maybe a super genre, if it's a subgenre itself.
- You can play the generally spoken "playables" with a player in the console, which then calls up the standard audio player of the operating system. Of course, it can't interact with every possible player out there, so it just pretends to. This means that if, for example, the track in the operating system player is over, my program will not notice it. You have to tell him yourself that the next track has to be played. It takes a little getting used to, I'll admit :).

I hope it is understandable that the console application in particular still leaves a lot to be desired (such as deleting individual elements). I only had time for a few double lessons at school. So also things like JUnit tests or Javadocs are unfortunately quite incomplete, which is honestly also not according to my taste. If I would've had more time I would certainly have paid attention to, for example, a significantly higher test coverage there. As mentioned, the project is only about showing that you can do it. Hopefully you can see that in the things I have done, such as the JUnit tests that do exist.
## Database
I built Commusify using Transact SQL (Microsoft SQL Server). As this is just a school project and nothing productive, I used mywindowshosting.com Microsoft SQL Server 60-day free trial as the online database. That's enough for me, because I hand in the project before anyway and it's also evaluated before the expiration. Unfortunately, there's only one GB of storage space, but as I said, the whole thing is not productive, you would of course have to invest money there.
### Entity Relationship Diagram
#### SSMS Version (original)
![SSMS ERD](https://raw.githubusercontent.com/jzelAdmin2006/Commusify/main/SQL/Commusify_SSMSERD.png)
#### phpMyAdmin Version (MySQL)
I converted the database to a MySQL database so I also have phpMyAdmin's ERD, which has the advantage that you can see exactly which table columns are referenced.
![phpMyAdmin ERD](https://raw.githubusercontent.com/jzelAdmin2006/Commusify/main/SQL/Commusify_phpmyadminERD.png)
## 
![My Signature](https://raw.githubusercontent.com/jzelAdmin2006/Commusify/main/Signature.svg)
