package com.textadventure.exeptions;

public class TypeDoesNotExistException extends Exception{
        private final String type;
       public TypeDoesNotExistException(String type) {
            this.type=type;
        }

        @Override
        public String getMessage() {
            return type+" existiert nicht";
        }
}
