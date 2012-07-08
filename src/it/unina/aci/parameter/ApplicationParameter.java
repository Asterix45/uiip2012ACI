package it.unina.aci.parameter;

import java.io.File;

public class ApplicationParameter {

	
public static String dirUser=System.getProperty("user.dir");	
public static String dirConf="conf";
public static String fileConfDB="poolMysql.properties";

public static String getNamePathFileConfDB(){
	
	return dirUser+File.separator+dirConf+File.separator+fileConfDB;
	
}

public static String getResourceNameFileConfDB(){
	
	return "/"+dirConf+"/"+fileConfDB;
	
}
	
	
	
	
}
