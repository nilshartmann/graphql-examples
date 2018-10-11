#! /bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}")" && pwd )"

TARGET_DIR=$DIR/target
FRONTEND_DIR=$( cd ${DIR}/../frontend && pwd )
BACKEND_DIR=$( cd ${DIR}/../backend && pwd )


echo $TARGET_DIR
echo $FRONTEND_DIR
echo $BACKEND_DIR

rm -rf $TARGET_DIR
rm -f $DIR/beeradvisor-app.zip 
mkdir $TARGET_DIR

cp -r $DIR/src/ $TARGET_DIR
cd $BACKEND_DIR && ./gradlew clean build && cp build/libs/beeradvisor-0.0.1-SNAPSHOT.jar $TARGET_DIR
cd $FRONTEND_DIR && yarn clean && yarn dist && cp -r public $TARGET_DIR

cd $TARGET_DIR && zip -r -X $DIR/beeradvisor-app.zip .