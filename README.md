# FpgaMacro
A [Chisel](https://www.chisel-lang.org/) package to describe verilog FPGA template and macro for hardened FPGA modules

# Install

## Use it locally (publish local)

To install it, clone it then publish local:
```bash
$ git clone https://github.com/Martoni/fpgamacro.git
$ cd fpgamacro
$ sbt publishLocal
```
Then add these lines in your `build.sbt` project :
```scala
//publish local https://github.com/Martoni/fpgamacro.git
libraryDependencies ++= Seq("org.armadeus" %% "fpgamacro" % "6.2.1")
```

# Library

