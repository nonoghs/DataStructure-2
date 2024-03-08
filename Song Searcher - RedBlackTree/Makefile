SingleSongInterface.class: SingleSongInterface.java
	javac SingleSongInterface.java

Backend.class: Backend.java
	javac Backend.java

SingleSong.class: SingleSong.java
	javac SingleSong.java

BackendDeveloperTests.class: BackendDeveloperTests.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java

BackendInterface.class: BackendInterface.java
	javac BackendInterface.java

runBDTests: BackendDeveloperTests.class BackendInterface.class Backend.class SingleSong.class SingleSongInterface.class
	javac SingleSongInterface.java
	javac Backend.java
	javac SingleSong.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java KeyList.java BinarySearchTree.java RedBlackTree.java IterableMultiKeyRBT.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

runFDTests:
	javac -cp .:../junit5.jar Frontend.java BackendPlaceholder.java SingleSongPlaceholder.java FrontendDeveloperTests.java KeyList.java BinarySearchTree.java RedBlackTree.java IterableMultiKeyRBT.java
	java -jar ../junit5.jar --class-path=. --select-class=FrontendDeveloperTests

clean:
	rm -f *.class
