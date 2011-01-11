JAVAC = javac -g
JAR = jar
SOURCES = AsciiException.java AsciiImage.java AsciiShop.java \
		  AsciiPoint.java AsciiConstants.java Operation.java \
		  ReplaceOperation.java OperationException.java \
		  ClearOperation.java LoadOperation.java \
		  MedianOperation.java FactoryException.java \
		  ClearFactory.java LoadFactory.java Factory.java \
		  FilterFactory.java ReplaceFactory.java BinaryOperation.java \
		  BinaryFactory.java Histogram.java FilterOperation.java \
		  FloodfillFactory.java FloodfillOperation.java AverageOperation.java
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
