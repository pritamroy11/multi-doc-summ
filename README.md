# multi-doc-summ


for testing the test-app project change the following entries

1. Open .classpath file and beside the entry **StandardVMType/jdk1.8.0.66**
modify it to your java version... Recommended to use JDK1.7


 <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/jdk1.7.0_45">

replace it with the jdk<version> of your system

2. Similarly for **org.eclipse.jst.server.tomcat.runtimeTarget/Apache Tomcat v8.0**
modify it to the apache version used.  Recommended to use and tomcat-7  

<classpathentry kind="con" path="org.eclipse.jst.server.core.container/org.eclipse.jst.server.tomcat.runtimeTarget/Apache Tomcat v6.0>
replace it with the apache tomcat <version>


then it will run fine

