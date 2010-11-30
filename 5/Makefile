JAVAC = javac -g
JAR = jar
SOURCES = AsciiException.java AsciiImage.java AsciiShop.java \
		  AsciiPoint.java AsciiConstants.java AsciiStack.java
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
