JAVAC = javac
JAR = jar
MODNAME = AsciiShop

all: $(MODNAME).class

$(MODNAME).class: $(MODNAME).java
	$(JAVAC) $(MODNAME).java

package: $(MODNAME).java
	$(JAR) Mcvf $(MODNAME).jar $(MODNAME).java

clean:
	rm -f $(MODNAME).class
	rm -f $(MODNAME).jar
