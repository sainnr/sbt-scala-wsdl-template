#!/usr/bin/env bash

# Following lines help the script to be executed either by SBT or directly, from any directory
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

# Expects: 1. package name, 2. WSDL URL, [optionally 3.] location of actual WSDL to replace one from URL
wsdl-import(){
    if [[ -z "$3" ]]
    then
        wsimport -s ../src/main/java -extension -p github.sainnr.wsdl.$1 \
            -XadditionalHeaders -Xnocompile $2
    else
        wsimport -s ../src/main/java -extension -p github.sainnr.wsdl.$1 \
            -XadditionalHeaders -Xnocompile -wsdllocation $2 $3
    fi
}

wsdl-import nl http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL

# Could be also something like this:
# wsdl-import nl http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL file:./wsdl_local/nl.xml
