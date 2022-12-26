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

	CREATE TABLE TRACK (
		ID INT IDENTITY,
		Title VARCHAR(MAX) NOT NULL,
		Audio VARBINARY(MAX) NOT NULL,
		FK_GenreID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_GenreID) REFERENCES GENRE(ID)
	);

	CREATE TABLE PLAYLIST (
		ID INT IDENTITY,
		Title VARCHAR(MAX) NOT NULL,
		PRIMARY KEY(ID)
	);

	CREATE TABLE PLAYLIST_TRACK (
		ID INT IDENTITY,
		FK_TrackID INT NOT NULL,
		FK_PlaylistID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_TrackID) REFERENCES TRACK(ID),
		FOREIGN KEY(FK_PlaylistID) REFERENCES PLAYLIST(ID)
	);

	CREATE TABLE ALBUM (
		ID INT IDENTITY,
		Title VARCHAR(MAX) NOT NULL,
		[Type] INT NOT NULL,
		PRIMARY KEY(ID)
	);

	CREATE TABLE ALBUMINTERPRETATION (
		ID INT IDENTITY,
		FK_AlbumID INT NOT NULL,
		FK_ArtistID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_AlbumID) REFERENCES ALBUM(ID),
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

	CREATE TABLE INTERPRETATION_BELONGS_ALBUM (
		ID INT IDENTITY,
		FK_AlbumID INT NOT NULL,
		FK_InterpretationID INT NOT NULL,
		PRIMARY KEY(ID),
		FOREIGN KEY(FK_AlbumID) REFERENCES ALBUM(ID),
		FOREIGN KEY(FK_InterpretationID) REFERENCES INTERPRETATION(ID)
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
		SELECT * FROM TRACK WHERE ID = @ID
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

	CREATE PROCEDURE SP_CREATE_PLAYLIST
		@Title VARCHAR(MAX)
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO PLAYLIST(Title) OUTPUT inserted.ID VALUES (@Title)
	END
	GO

	CREATE PROCEDURE SP_ADD_PLAYLIST_TRACK
		@PlaylistID INT,
		@TrackID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO PLAYLIST_TRACK(FK_PlaylistID, FK_TrackID) VALUES (@PlaylistID, @TrackID)
	END
	GO

	CREATE PROCEDURE SP_FIND_PLAYLIST
		@ID INT
	AS
	BEGIN
		SET NOCOUNT ON;
		SELECT * FROM PLAYLIST P
		JOIN PLAYLIST_TRACK ON FK_PlaylistID = P.ID
		WHERE P.ID = @ID
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
	END
	GO