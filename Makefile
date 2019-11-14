SBT = sbt
#BACKEND=--backend-name verilator
VCD=--generate-vcd-output on

MDIO_DIR=../MDIO
SPI2WB_DIR=../spi2wb
WBPLUMBING=../WbPlumbing
SCALAPATH=src/main/scala

#hdl: .dependencies
#	$(SBT) "runMain machxo3.MachXO3"

publishlocal:
	$(SBT) publishLocal

mrproper: cleanoutput
	-rm .dependencies
	-rm *.anno.json
	-rm *.fir
	-rm *.v
	-rm -rf target
	-rm -rf test_run_dir
	-rm -rf project
