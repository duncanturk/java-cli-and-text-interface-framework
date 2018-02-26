package com.duncanturk.cli.api.communicator;

public enum CounterStyle {
    AUTO, DECIMAL, LOWER_ALPHA;

    String get(int i) {
        switch (this) {
            case LOWER_ALPHA:
                return inLowerAlpha(i);
            case AUTO:
            case DECIMAL:
            default:
                return (1 + i) + "";
        }
    }

    private String inLowerAlpha(int a) {
        if (a == 0)
            return "a";

        StringBuilder sb = new StringBuilder();
        sb.append('a');
        while (a > 0) {
            sb.setCharAt(0, (char) (sb.charAt(0) + 1));
            for (int off = 0; sb.charAt(off) == 'z' + 1; ++off) {
                sb.setCharAt(off, 'a');
                if (sb.length() <= off + 1)
                    sb.append('a');
                else
                    sb.setCharAt(off + 1, (char) (sb.charAt(off + 1) + 1));
            }
            --a;
        }
        sb.reverse();
        return sb.toString();
    }
}
