JAVAC = javac
JAR = jar
SOURCES = AsciiException.java AsciiImage.java AsciiShop.java \
		  AsciiCoord.java AsciiConstants.java
CLASSES = $(SOURCES:.java=.class)
MODNAME = AsciiShop

all: $(CLASSES)

%.class: %.java
	$(JAVAC) $<

package: $(SOURCES)
	$(JAR) -Mcvf $(MODNAME).jar $(SOURCES)

clean:
	rm -f $(CLASSES)
	rm -f $(MODNAME).jar
