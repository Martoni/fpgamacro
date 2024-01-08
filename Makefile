SBT=sbt
CLASSEUR=classeur
PANDOC=pandoc 

DOCSOURCES=pandoc_cfg.md \
		 README.md \
		 src/main/scala/fpgamacro/generic/README.md \
		 src/main/scala/fpgamacro/machxo3/README.md \
		 src/main/scala/fpgamacro/ecp5/README.md \
		 src/main/scala/fpgamacro/gowin/README.md \
		 src/main/scala/fpgamacro/gatemate/README.md \
		 src/main/scala/fpgamacro/ice40/README.md \
		 src/main/scala/fpgamacro/eos_s3/README.md

publishlocal:
	$(SBT) publishLocal

mrproper: 
	-rm -rf project

fpgamacro.pdf: $(DOCSOURCES)
	$(PANDOC) $(DOCSOURCES) -o $@ 

merged_doc.md: $(DOCSOURCES)
	$(CLASSEUR) -m $(DOCSOURCES) -o $@ 

split:
	$(CLASSEUR) -s merged_doc.md
