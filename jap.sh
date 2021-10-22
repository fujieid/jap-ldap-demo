#!/bin/bash

help(){
  echo "--------------------------------------------------------------------------"
  echo ""
  echo "usage: ./jap.sh p"
  echo ""
  echo "-p   git push."
  echo ""
  echo "--------------------------------------------------------------------------"
}

case "$1" in
  'p')
    bin/push.sh
    git push origin master
	;;
  'ldap')
    cd docker && docker-compose -p ldap up -d
	;;
  *)
    help
esac
