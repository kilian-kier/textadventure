package com.textadventure.exeptions;

public class TypeNotValidException extends Exception{
        private final String type;
        private final String parameter;
        public TypeNotValidException(String type, String parameter) {
            this.type = type;
            this.parameter = parameter;
        }

        @Override
        public String getMessage() {
            return "Typ "+type +" enthält keine Parameter "+parameter;
        }
}
