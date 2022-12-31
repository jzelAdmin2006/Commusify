# Commusify
Commusify - A music service truly connected to it's community.
## About
This was a school project at BBZW Sursee.

<img src="https://raw.githubusercontent.com/jzelAdmin2006/Commusify/main/Important.png" width="200"> https://bit.ly/3Q6AAzC

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
## Design (UML)
In addition to programming, I also had the task of representing the whole thing graphically with UML. We got to know two kinds of diagrams: the class and sequence diagrams. We were able to create these with the tool Enterprise Architect from Sparx Systems. Normally, with a piece of software, you create several such diagrams in order to get different views of it. I decided to create four diagrams per type for my Commusify project. The Enterprise Architect projects I created can all be found in https://github.com/jzelAdmin2006/Commusify/tree/main/UML.
### Class diagram
This is where I decided to really get the most out of Enterprise Architect. I even had to use a new version as a trial to generate the diagrams of my project because our version of Enterprise Architect somehow couldn't read my project as it didn't understand some syntax. However, I was still able to edit the diagrams with our version. I was always able to polish the whole thing and add some things, such as the correct multiplicities, important instantiations or other relationships.
#### Main overview
This diagram contains all classes of the main package, so all classes except for the tests.

#### Framework overview
This diagram contains all classes of the framework. This includes all classes that are responsible for the basic structure of Commusify and often interact with the database.

#### Console app overview
This diagram contains all classes of the user interface. This primarily includes all Commusify commands.

#### Package seperation
These are actually multiple diagrams, but they are all very small. Namely, I packed the classes of each of the main subpackages in a class diagram, so again everything except the tests. Some packages are more interesting than others. Here are the most important packages, you can of course look at the rest in the Enterprise Architect project file. Here I somehow had the problem that I couldn't copy the diagrams correctly as images from the old Enterprise Architect. So I used the trial version of the new one for that. It looks a little different, but of course it's exactly the same.

### Sequence diagram
I had to be a lot more specific here than with the class diagrams. One difference with sequence diagrams is that they are also about time and not just about structure. Of course, there are many ways in which my software can run in a span of time, it all depends on user input. The way I did it wasn't entirely correct, but since I couldn't really represent the entire application behaviour in this way, I had to pretend that the user was always instantiating the first object. After all, there are often several possibilities from where the objects can be instantiated. So I've always assumed that the user does it. Here I had the same problem everywhere with copying the diagrams as images as with the package separation. So I also used the newer Enterprise Architect trial version for this.
#### Existing track instantiation
This sequence diagram shows how a normal track already existing in the database is instantiated with the ID.

#### New double long play creation
This sequence diagram shows how a new double long play album is instantiated, which does not yet exist in the database and is therefore written there.

#### Subgenre creation via command execution
This diagram shows what happens when the CreateSubGenre Command is executed, obviously creating a new subgenre and writing it to the database.

#### Searching for artists via command execution
This shows what happens when the user searches for artists using the search command. Theoretically, there would be also the enum 'KnownSearchable' in the create(searchable):Searcher method of the search command here, but unfortunately Enterprise Architect could not recognize this enum for some reason. However, only two relevant methods would be executed there, so that's not too tragic.

## 
![My Signature](https://raw.githubusercontent.com/jzelAdmin2006/Commusify/main/Signature.svg)
