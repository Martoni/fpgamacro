SBT = sbt

publishlocal:
	$(SBT) publishLocal

mrproper: 
	-rm -rf project
