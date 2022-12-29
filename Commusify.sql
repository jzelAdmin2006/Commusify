-- Drop everything in the database
	/* Drop all non-system stored procs */
	DECLARE @name VARCHAR(128)
	DECLARE @SQL VARCHAR(254)

	SELECT @name = (SELECT TOP 1 [name] FROM sysobjects WHERE [type] = 'P' AND category = 0 ORDER BY [name])

	WHILE @name is not null
	BEGIN
		SELECT @SQL = 'DROP PROCEDURE [dbo].[' + RTRIM(@name) +']'
		EXEC (@SQL)
		PRINT 'Dropped Procedure: ' + @name
		SELECT @name = (SELECT TOP 1 [name] FROM sysobjects WHERE [type] = 'P' AND category = 0 AND [name] > @name ORDER BY [name])
	END
	GO

	/* Drop all views */
	DECLARE @name VARCHAR(128)
	DECLARE @SQL VARCHAR(254)

	SELECT @name = (SELECT TOP 1 [name] FROM sysobjects WHERE [type] = 'V' AND category = 0 ORDER BY [name])

	WHILE @name IS NOT NULL
	BEGIN
		SELECT @SQL = 'DROP VIEW [dbo].[' + RTRIM(@name) +']'
		EXEC (@SQL)
		PRINT 'Dropped View: ' + @name
		SELECT @name = (SELECT TOP 1 [name] FROM sysobjects WHERE [type] = 'V' AND category = 0 AND [name] > @name ORDER BY [name])
	END
	GO

	/* Drop all functions */
	DECLARE @name VARCHAR(128)
	DECLARE @SQL VARCHAR(254)

	SELECT @name = (SELECT TOP 1 [name] FROM sysobjects WHERE [type] IN (N'FN', N'IF', N'TF', N'FS', N'FT') AND category = 0 ORDER BY [name])

	WHILE @name IS NOT NULL
	BEGIN
		SELECT @SQL = 'DROP FUNCTION [dbo].[' + RTRIM(@name) +']'
		EXEC (@SQL)
		PRINT 'Dropped Function: ' + @name
		SELECT @name = (SELECT TOP 1 [name] FROM sysobjects WHERE [type] IN (N'FN', N'IF', N'TF', N'FS', N'FT') AND category = 0 AND [name] > @name ORDER BY [name])
	END
	GO

	/* Drop all Foreign Key constraints */
	DECLARE @name VARCHAR(128)
	DECLARE @constraint VARCHAR(254)
	DECLARE @SQL VARCHAR(254)

	SELECT @name = (SELECT TOP 1 TABLE_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'FOREIGN KEY' ORDER BY TABLE_NAME)

	WHILE @name is not null
	BEGIN
		SELECT @constraint = (SELECT TOP 1 CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'FOREIGN KEY' AND TABLE_NAME = @name ORDER BY CONSTRAINT_NAME)
		WHILE @constraint IS NOT NULL
		BEGIN
			SELECT @SQL = 'ALTER TABLE [dbo].[' + RTRIM(@name) +'] DROP CONSTRAINT [' + RTRIM(@constraint) +']'
			EXEC (@SQL)
			PRINT 'Dropped FK Constraint: ' + @constraint + ' on ' + @name
			SELECT @constraint = (SELECT TOP 1 CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'FOREIGN KEY' AND CONSTRAINT_NAME <> @constraint AND TABLE_NAME = @name ORDER BY CONSTRAINT_NAME)
		END
	SELECT @name = (SELECT TOP 1 TABLE_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'FOREIGN KEY' ORDER BY TABLE_NAME)
	END
	GO

	/* Drop all Primary Key constraints */
	DECLARE @name VARCHAR(128)
	DECLARE @constraint VARCHAR(254)
	DECLARE @SQL VARCHAR(254)

	SELECT @name = (SELECT TOP 1 TABLE_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'PRIMARY KEY' ORDER BY TABLE_NAME)

	WHILE @name IS NOT NULL
	BEGIN
		SELECT @constraint = (SELECT TOP 1 CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'PRIMARY KEY' AND TABLE_NAME = @name ORDER BY CONSTRAINT_NAME)
		WHILE @constraint is not null
		BEGIN
			SELECT @SQL = 'ALTER TABLE [dbo].[' + RTRIM(@name) +'] DROP CONSTRAINT [' + RTRIM(@constraint)+']'
			EXEC (@SQL)
			PRINT 'Dropped PK Constraint: ' + @constraint + ' on ' + @name
			SELECT @constraint = (SELECT TOP 1 CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'PRIMARY KEY' AND CONSTRAINT_NAME <> @constraint AND TABLE_NAME = @name ORDER BY CONSTRAINT_NAME)
		END
	SELECT @name = (SELECT TOP 1 TABLE_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'PRIMARY KEY' ORDER BY TABLE_NAME)
	END
	GO

	/* Drop all tables */
	DECLARE @name VARCHAR(128)
	DECLARE @SQL VARCHAR(254)

	SELECT @name = (SELECT TOP 1 [name] FROM sysobjects WHERE [type] = 'U' AND category = 0 ORDER BY [name])

	WHILE @name IS NOT NULL
	BEGIN
		SELECT @SQL = 'DROP TABLE [dbo].[' + RTRIM(@name) +']'
		EXEC (@SQL)
		PRINT 'Dropped Table: ' + @name
		SELECT @name = (SELECT TOP 1 [name] FROM sysobjects WHERE [type] = 'U' AND category = 0 AND [name] > @name ORDER BY [name])
	END
	GO

-- Create tables

	CREATE TABLE [USER] (
		ID INT IDENTITY,
		UserName VARCHAR(450) UNIQUE NOT NULL,
		PasswordHash VARCHAR(MAX) NOT NULL,
		FirstName VARCHAR(MAX) NOT NULL,
		LastName VARCHAR(MAX) NOT NULL,
		Email VARCHAR(MAX) NOT NULL,
		PRIMARY KEY(ID),
		CONSTRAINT EmailFormat CHECK(Email LIKE '%___@___%.__%')
	);

	CREATE TABLE ARTIST (
		ID INT IDENTITY,
		[Name] VARCHAR(MAX) NOT NULL,
		PRIMARY KEY(ID)
	);

	CREATE TABLE ARTIST_MEMBER (
		ID INT IDENTITY,
		FK_UserID INT NOT NULL,
		FK_ArtistID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_UserID) REFERENCES [USER](ID),
		FOREIGN KEY(FK_ArtistID) REFERENCES ARTIST(ID)
	);

	CREATE TABLE GENRE (
		ID INT IDENTITY,
		Designation VARCHAR(MAX) NOT NULL,
		PRIMARY KEY(ID)
	);
	
	SET IDENTITY_INSERT GENRE ON;
	GO

	INSERT INTO GENRE(ID, Designation) VALUES (0, 'Invalid genre');

	SET IDENTITY_INSERT GENRE OFF;
	GO

	CREATE TABLE TRACK (
		ID INT IDENTITY,
		Title VARCHAR(MAX) NOT NULL,
		Audio VARBINARY(MAX) NOT NULL,
		FK_GenreID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_GenreID) REFERENCES GENRE(ID)
	);

	SET IDENTITY_INSERT TRACK ON;
	GO

	INSERT INTO TRACK(ID, Title, Audio, FK_GenreID) VALUES (0, 'Invalid track', 1, 0);

	SET IDENTITY_INSERT TRACK OFF;
	GO

	CREATE TABLE PLAYABLE_LIST (
		ID INT IDENTITY,
		Title VARCHAR(MAX) NOT NULL,
		PRIMARY KEY(ID)
	);
	GO

	SET IDENTITY_INSERT PLAYABLE_LIST ON;
	GO

	INSERT INTO PLAYABLE_LIST(ID, Title) VALUES (0, 'Invalid playable list');

	SET IDENTITY_INSERT PLAYABLE_LIST OFF;
	GO

	CREATE TABLE PLAYABLE_LIST_PLAYABLE (
		ID INT IDENTITY,
		FK_TrackID INT NOT NULL DEFAULT 0,
		FK_ContainedPlayableListID INT NOT NULL DEFAULT 0,
		FK_PlayableListID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_TrackID) REFERENCES TRACK(ID),
		FOREIGN KEY(FK_ContainedPlayableListID) REFERENCES PLAYABLE_LIST(ID),
		FOREIGN KEY(FK_PlayableListID) REFERENCES PLAYABLE_LIST(ID),
		CHECK(NOT(FK_TrackID = 0 AND FK_ContainedPlayableListID = 0))
	);

	CREATE TABLE ALBUM (
		FK_PlayableListID INT NOT NULL,
		[Type] INT NOT NULL,
		PRIMARY KEY(FK_PlayableListID),
		FOREIGN KEY(FK_PlayableListID) REFERENCES PLAYABLE_LIST(ID)
	);

	CREATE TABLE MIX_TAPE (
		FK_AlbumID INT NOT NULL,
		[Description] VARCHAR(MAX) NOT NULL,
		PRIMARY KEY(FK_AlbumID),
		FOREIGN KEY(FK_AlbumID) REFERENCES ALBUM(FK_PlayableListID)
	);

	CREATE TABLE ALBUMINTERPRETATION (
		ID INT IDENTITY,
		FK_AlbumID INT NOT NULL,
		FK_ArtistID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_AlbumID) REFERENCES ALBUM(FK_PlayableListID),
		FOREIGN KEY(FK_ArtistID) REFERENCES ARTIST(ID)
	);

	CREATE TABLE INTERPRETATION (
		ID INT IDENTITY,
		FK_TrackID INT NOT NULL,
		FK_ArtistID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_TrackID) REFERENCES TRACK(ID),
		FOREIGN KEY(FK_ArtistID) REFERENCES ARTIST(ID)
	);

	CREATE TABLE TRACK_EDIT (
		ID INT IDENTITY,
		FK_OriginalTrackID INT NOT NULL,
		FK_EditedTrackID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_OriginalTrackID) REFERENCES TRACK(ID),
		FOREIGN KEY(FK_EditedTrackID) REFERENCES TRACK(ID)
	);

	CREATE TABLE SUB_GENRE (
		ID INT IDENTITY,
		FK_SubGenreID INT NOT NULL,
		FK_SuperGenreID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_SubGenreID) REFERENCES GENRE(ID),
		FOREIGN KEY(FK_SuperGenreID) REFERENCES GENRE(ID)
	);

-- Create stored procedures
	SET ANSI_NULLS ON
	GO
	SET QUOTED_IDENTIFIER ON
	GO
	CREATE PROCEDURE SP_INSERT_AUDIO
		@AUDIO VARBINARY(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO TRACK(Audio) VALUES (@AUDIO)
	END
	GO

	SET ANSI_NULLS ON
	GO
	SET QUOTED_IDENTIFIER ON
	GO
	CREATE PROCEDURE SP_FIND_TRACK
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM TRACK T
		JOIN INTERPRETATION ON FK_TrackID = T.ID
		WHERE T.ID = @ID
	END
	GO

	SET ANSI_NULLS ON
	GO
	SET QUOTED_IDENTIFIER ON
	GO
	CREATE PROCEDURE SP_FIND_GENRE
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM GENRE WHERE ID = @ID
	END
	GO

	CREATE PROCEDURE SP_CREATE_GENRE
		@Designation VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO GENRE(Designation) OUTPUT inserted.ID VALUES (@Designation)
	END
	GO

	CREATE PROCEDURE SP_CREATE_USER
		@UserName VARCHAR(MAX),
		@PasswordHash INT,
		@FirstName VARCHAR(MAX),
		@LastName VARCHAR(MAX),
		@Email VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO [USER](UserName, PasswordHash, FirstName, LastName, Email) OUTPUT inserted.ID VALUES (@UserName, @PasswordHash, @FirstName, @LastName, @Email)
	END
	GO

	CREATE PROCEDURE SP_FIND_USER
		@ID VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM [USER] WHERE ID = @ID
	END
	GO

	CREATE PROCEDURE SP_FIND_USER_BY_USERNAME
		@UserName VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM [USER] WHERE UserName = @UserName
	END
	GO

	CREATE PROCEDURE SP_CREATE_ARTIST
		@Name VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO ARTIST([Name]) OUTPUT inserted.ID VALUES (@Name)
	END
	GO

	CREATE PROCEDURE SP_ADD_ARTIST_MEMBER
		@ArtistID INT,
		@MemberUserID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO ARTIST_MEMBER(FK_ArtistID, FK_UserID) VALUES (@ArtistID, @MemberUserID)
	END
	GO

	CREATE PROCEDURE SP_FIND_ARTIST
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM ARTIST A
		JOIN ARTIST_MEMBER ON FK_ArtistID = A.ID
		WHERE A.ID = @ID
	END
	GO

	CREATE PROCEDURE SP_CREATE_TRACK
		@Title VARCHAR(MAX),
		@Audio VARBINARY(MAX),
		@GenreID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO TRACK(Title, Audio, FK_GenreID) OUTPUT inserted.ID VALUES (@Title, @Audio, @GenreID)
	END
	GO

	CREATE PROCEDURE SP_CREATE_PLAYABLE_LIST
		@Title VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO PLAYABLE_LIST(Title) OUTPUT inserted.ID VALUES (@Title)
	END
	GO

	CREATE PROCEDURE SP_ADD_PLAYABLE_LIST_PLAYABLE
		@PlayableListID INT,
		@PlayableID INT,
		@IsTrack BIT
	AS
	BEGIN
		SET NOCOUNT ON;
		IF @IsTrack = 1
			INSERT INTO PLAYABLE_LIST_PLAYABLE(FK_PlayableListID, FK_TrackID) VALUES (@PlayableListID, @PlayableID)
		ELSE
			INSERT INTO PLAYABLE_LIST_PLAYABLE(FK_PlayableListID, FK_ContainedPlayableListID) VALUES (@PlayableListID, @PlayableID)
	END
	GO

	CREATE PROCEDURE SP_FIND_PLAYABLE_LIST
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT 
			P.ID,
			Title,
			PLP.ID AS 'PlayableListPlayableID',
			FK_TrackID,
			FK_ContainedPlayableListID,
			FK_PlayableListID
		FROM PLAYABLE_LIST P
		JOIN PLAYABLE_LIST_PLAYABLE PLP ON FK_PlayableListID = P.ID
		WHERE P.ID = @ID
	END
	GO

	CREATE PROCEDURE SP_CREATE_ALBUM
		@Type INT,
		@PlayableListID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO ALBUM([Type], FK_PlayableListID) VALUES (@Type, @PlayableListID)
	END
	GO

	CREATE PROCEDURE SP_ADD_ALBUM_INTERPRETER
		@AlbumID INT,
		@InterpreterArtistID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO ALBUMINTERPRETATION(FK_AlbumID, FK_ArtistID) VALUES (@AlbumID, @InterpreterArtistID)
	END
	GO

	CREATE PROCEDURE SP_FIND_ALBUM
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM ALBUMINTERPRETATION
		WHERE FK_AlbumID = @ID
	END
	GO

	CREATE PROCEDURE SP_CREATE_MIX_TAPE
		@ID INT,
		@Description VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO MIX_TAPE(FK_AlbumID, [Description]) VALUES (@ID, @Description)
	END
	GO

	CREATE PROCEDURE SP_FIND_MIX_TAPE
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM MIX_TAPE
		WHERE FK_AlbumID = @ID
	END
	GO

	CREATE PROCEDURE SP_ADD_INTERPRETER
		@TrackID INT,
		@InterpreterArtistID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO INTERPRETATION(FK_TrackID, FK_ArtistID) VALUES (@TrackID, @InterpreterArtistID)
	END
	GO

	CREATE PROCEDURE SP_ADD_TRACK_EDIT
		@EditedTrackID INT,
		@OriginalTrackID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO TRACK_EDIT(FK_EditedTrackID, FK_OriginalTrackID) VALUES (@EditedTrackID, @OriginalTrackID)
	END
	GO

	CREATE PROCEDURE SP_FIND_TRACK_EDIT
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM TRACK_EDIT
		WHERE @ID = FK_EditedTrackID
	END
	GO

	CREATE PROCEDURE SP_CREATE_SUB_GENRE
		@SubGenreID INT,
		@SuperGenreID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO SUB_GENRE(FK_SubGenreID, FK_SuperGenreID) VALUES (@SubGenreID, @SuperGenreID)
	END
	GO

	CREATE PROCEDURE SP_FIND_SUB_GENRE
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM SUB_GENRE
		WHERE FK_SubGenreID = @ID
	END
	GO

	CREATE PROCEDURE SP_FIND_ALBUM_TYPE
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM ALBUM
		WHERE FK_PlayableListID = @ID
	END
	GO

	CREATE PROCEDURE SP_SEARCH_GENRE
		@Search VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM GENRE
		WHERE LOWER(Designation) LIKE LOWER('%' + @Search +'%')
	END
	GO

	CREATE PROCEDURE SP_SEARCH_PLAYABLE_LIST
		@Search VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM PLAYABLE_LIST
		WHERE LOWER(Title) LIKE LOWER('%'+ @Search +'%')
	END
	GO

	CREATE PROCEDURE SP_SEARCH_TRACK
		@Search VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM TRACK
		WHERE LOWER(Title) LIKE LOWER('%'+ @Search +'%')
	END
	GO

	CREATE PROCEDURE SP_SEARCH_ARTIST
		@Search VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM ARTIST
		WHERE LOWER([Name]) LIKE LOWER('%'+ @Search +'%')
	END
	GO

	CREATE PROCEDURE SP_SEARCH_USER
		@Search VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM [USER]
		WHERE LOWER(UserName) LIKE LOWER('%'+ @Search +'%') OR
		LOWER(FirstName) LIKE LOWER('%'+ @Search +'%') OR
		LOWER(LastName) LIKE LOWER('%'+ @Search +'%') OR
		LOWER(Email) LIKE LOWER('%'+ @Search +'%')
	END
	GO

	CREATE PROCEDURE SP_GENRE_ID_EXISTS
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT
			CAST(
				CASE WHEN
					COUNT(*) > 0
					THEN 1
					ELSE 0
				END
				AS BIT
			)
			AS 'ID_EXISTS'
		FROM GENRE
		WHERE ID = @ID
	END
	GO

	CREATE PROCEDURE SP_ARTIST_ID_EXISTS
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT
			CAST(
				CASE WHEN
					COUNT(*) > 0
					THEN 1
					ELSE 0
				END
				AS BIT
			)
			AS 'ID_EXISTS'
		FROM ARTIST
		WHERE ID = @ID
	END
	GO

	CREATE PROCEDURE SP_TRACK_ID_EXISTS
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT
			CAST(
				CASE WHEN
					COUNT(*) > 0
					THEN 1
					ELSE 0
				END
				AS BIT
			)
			AS 'ID_EXISTS'
		FROM TRACK
		WHERE ID = @ID
	END
	GO

	CREATE PROCEDURE SP_PLAYABLE_LIST_ID_EXISTS
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT
			CAST(
				CASE WHEN
					COUNT(*) > 0
					THEN 1
					ELSE 0
				END
				AS BIT
			)
			AS 'ID_EXISTS'
		FROM PLAYABLE_LIST
		WHERE ID = @ID
	END
	GO

	CREATE PROCEDURE SP_RESETALL
	AS
	BEGIN
		SET NOCOUNT ON;
		SET QUOTED_IDENTIFIER ON;
		EXEC sp_MSforeachtable 'SET QUOTED_IDENTIFIER ON; ALTER TABLE ? NOCHECK CONSTRAINT ALL'  
		EXEC sp_MSforeachtable 'SET QUOTED_IDENTIFIER ON; ALTER TABLE ? DISABLE TRIGGER ALL'  
		EXEC sp_MSforeachtable 'SET QUOTED_IDENTIFIER ON; DELETE FROM ?'  
		EXEC sp_MSforeachtable 'SET QUOTED_IDENTIFIER ON; ALTER TABLE ? CHECK CONSTRAINT ALL'  
		EXEC sp_MSforeachtable 'SET QUOTED_IDENTIFIER ON; ALTER TABLE ? ENABLE TRIGGER ALL' 
		EXEC sp_MSforeachtable 'SET QUOTED_IDENTIFIER ON';

		IF NOT EXISTS (
			SELECT
				*
			FROM
				SYS.IDENTITY_COLUMNS
				JOIN SYS.TABLES ON SYS.IDENTITY_COLUMNS.Object_ID = SYS.TABLES.Object_ID
			WHERE
				SYS.TABLES.Object_ID = OBJECT_ID('?') AND SYS.IDENTITY_COLUMNS.Last_Value IS NULL
		)
		AND OBJECTPROPERTY( OBJECT_ID('?'), 'TableHasIdentity' ) = 1

			DBCC CHECKIDENT ('?', RESEED, 0) WITH NO_INFOMSGS;
			
		SET IDENTITY_INSERT GENRE ON;

		INSERT INTO GENRE(ID, Designation) VALUES (0, 'Invalid genre');

		SET IDENTITY_INSERT GENRE OFF;
		
		SET IDENTITY_INSERT TRACK ON;

		INSERT INTO TRACK(ID, Title, Audio, FK_GenreID) VALUES (0, 'Invalid track', 1, 0);

		SET IDENTITY_INSERT TRACK OFF;
		
		SET IDENTITY_INSERT PLAYABLE_LIST ON;

		INSERT INTO PLAYABLE_LIST(ID, Title) VALUES (0, 'Invalid playable list');

		SET IDENTITY_INSERT PLAYABLE_LIST OFF;
	END
	GO