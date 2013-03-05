#
# define compiler and compiler flag variables
#

JC = javac

CLASS_PATH = ./classes
JFLAGS = -g -classpath $(CLASS_PATH) -d
CLASS_DIR = classes/
SRC_DIR = src/


CLASSES = $(SRC_DIR)rmi/util/DataTable.java \
        $(SRC_DIR)rmi/util/RemoteException.java \
        $(SRC_DIR)rmi/util/Remote.java \
        $(SRC_DIR)rmi/util/Message.java \
        $(SRC_DIR)rmi/util/StubInfo.java \
        $(SRC_DIR)rmi/util/MessageProcessor.java \
        $(SRC_DIR)rmi/util/ServerSocketConection.java \
        $(SRC_DIR)rmi/util/ThreadPool.java \
        $(SRC_DIR)rmi/naming/RegistryMessageProcessor.java \
		$(SRC_DIR)rmi/naming/RMIRegistry.java \
        $(SRC_DIR)rmi/client/SocketConnection.java \
        $(SRC_DIR)rmi/client/ProxyHandler.java \
        $(SRC_DIR)rmi/client/Registry.java\
		$(SRC_DIR)rmi/server/ServerMessageProcessor.java \
		$(SRC_DIR)rmi/server/RMIServer.java \
        $(SRC_DIR)example/IWork.java \
        $(SRC_DIR)example/IPeople.java \
        $(SRC_DIR)example/Work.java \
        $(SRC_DIR)example/People.java \
        $(SRC_DIR)example/IWorkFactory.java \
        $(SRC_DIR)example/IPeopleFactory.java \
        $(SRC_DIR)example/WorkFactory.java \
        $(SRC_DIR)example/PeopleFactory.java \
        $(SRC_DIR)example/ServerTestMain.java \
        $(SRC_DIR)example/ClientTestMain.java \
        
		
		

#
# Clear any default targets for building .class files from .java files; we 
# will provide our own target entry to do this in this makefile.
# make has a set of default targets for different suffixes (like .c.o) 
# Currently, clearing the default for .java.class is not necessary since 
# make does not have a definition for this target, but later versions of 
# make may, so it doesn't hurt to make sure that we clear any default 
# definitions for these
#

.SUFFIXES: .java .class


#
# Here is our target entry for creating .class files from .java files 
# This is a target entry that uses the suffix rule syntax:
#	DSTS:
#		rule
#  'TS' is the suffix of the target file, 'DS' is the suffix of the dependency 
#  file, and 'rule'  is the rule for building a target	
# '$*' is a built-in macro that gets the basename of the current target 
# Remember that there must be a < tab > before the command line ('rule') 
#

.java.class:
	$(JC) $(JFLAGS) $(CLASS_DIR) $*.java
#
# the default make target entry
#

default: classes


#
# This target entry uses Suffix Replacement within a macro: 
# $(name:string1=string2)
# 	In the words in the macro named 'name' replace 'string1' with 'string2'
# Below we are replacing the suffix .java of all words in the macro CLASSES 
# with the .class suffix
#

classes: $(CLASSES:.java=.class)

#
# RM is a predefined macro in make (RM = rm -f)
#

clean:
	$(RM) -r $(CLASS_DIR)*
