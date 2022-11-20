package oop.cw1_2223.assignment;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileNameDate extends Methods{

    public theDate compute(File file) {
        return parseDateFromFileName(file.getName());
    }

    private theDate parseDateFromFileName(final String filename) {
        final List<LocalDate> possibles = new ArrayList<>();
        final ArrayList<String> fragments = new ArrayList<String>();
        splitByWordsAndNumbers( filename, fragments, false);
        for (int i = 0; i < fragments.size(); i++) {
            final String fragment = fragments.get(i);
            final char ch = fragment.charAt(0);
            boolean keep = true;
            if (Character.isLetter(ch)) {
                final int mi = identifyMonthName(fragment);
                if (mi < 0) {
                    // not a month, discard fragment
                    keep = false;
                } else {
                    // month name identified; replace with number
                    fragments.set(i, String.valueOf(mi + 1));
                }
            } else if (!Character.isDigit(ch)) {
                keep = false;
            } else if (ch == '0') {
                // discard strings consisting only of zeroes
                keep = trim( fragment, "0", true, false).length() != 0;
            }
            if (!keep) {
                fragments.remove(i); i--;
            }
        }
        // we now only have digit fragments
        // first, look for eight digit fragments
        for (final String fragment : fragments) {
            if (fragment.length() == 8) {
                try {
                    LocalDate date = LocalDate.parse(fragment, DateTimeFormatter.BASIC_ISO_DATE);
                    possibles.add(date);
                } catch (final DateTimeParseException ignored) {
                }
            }
        }
        // now look for triplets that form valid dates
        String[] temp = new String[3];
        for (int i = 0; i < fragments.size() - 2; i++) {
            fragments.subList(i, i + 3).toArray(temp);
            LocalDate date = tryDate(temp, 0, 2); // year month day
            if (date != null) {
                possibles.add(date);
            }
            date = tryDate(temp, 2, 0); // day month year
            if (date != null) {
                possibles.add(date);
            }
        }
        if (possibles.size() == 1) {
            LocalDate date = possibles.get(0);
            dateDestination = new theDate(date.getYear(), date.getMonthValue(),date.getDayOfMonth(),"File name");
            return dateDestination;
        } else {
            return null;
        }
    }

    private LocalDate tryDate(String[] fragments, int year, int day) {
        for (int j = 0; j < fragments.length; j++) {
            fragments[j] = trim( fragments[j], "0", true, false);
        }
        if (fragments[year].length() == 4 && fragments[1].length() <= 2 && fragments[day].length() <= 2) {
            final int[] n = new int[3];
            for(int j = 0; j < 3; j++) {
                n[j] = Integer.parseInt( fragments[j]);
            }
            try {
                return LocalDate.of(n[year], n[1], n[day]);
            } catch(final DateTimeException x) {
                return null;
            }
        }
        return null;
    }

    private int identifyMonthName(String fragment) {
        fragment = fragment.toLowerCase();
        String[] names = new String[] {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December" };
        for (int i = 0; i < names.length; i++) {
            String name = names[i].toLowerCase();
            if (fragment.length() == 3) {
                name = name.substring(0,3);
            }
            if (name.equals(fragment)) {
                return i;
            }
        }
        return -1;
    }

    private String trim( final CharSequence target, final String chars, final boolean leading, final boolean trailing) {
        final StringBuilder builder = new StringBuilder( target);
        if ( leading) {
            while( (builder.length() > 0) && (chars.indexOf( builder.charAt(0)) != -1)) { builder.delete(0, 1); }
        }
        if ( trailing) {
            while( (builder.length() > 0) && (chars.indexOf( builder.charAt(builder.length() - 1)) != -1)) { builder.delete(builder.length() - 1, builder.length()); }
        }
        return builder.toString();
    }

    private int splitByWordsAndNumbers(final CharSequence target, final Collection<String> toAddTo, final boolean allowPointInNumbers) {
        final StringBuilder builder = new StringBuilder();
        final int LETTERS = 2;
        final int DIGITS = 1;
        final int OTHER = 0;
        int gatheringMode = OTHER;
        int count = 0;
        int index = 0;
        while( index < target.length()) {
            final char c = target.charAt( index++);
            if ( Character.isLetter( c)) {
                switch ( gatheringMode) {
                    case LETTERS:
                        builder.append(c);
                        break;
                    case DIGITS:
                    case OTHER:
                        if ( builder.length() > 0) {
                            toAddTo.add( builder.toString());
                            count++;
                        }
                        builder.setLength( 0);
                        builder.append( c);
                        gatheringMode = LETTERS;
                        break;
                    default:
                        assert( false);
                        break;
                }
            } else if ( Character.isDigit( c) ||( allowPointInNumbers && ( c == '.'))) {
                switch ( gatheringMode) {
                    case DIGITS:
                        builder.append(c);
                        break;
                    case LETTERS:
                    case OTHER:
                        if ( builder.length() > 0) {
                            toAddTo.add( builder.toString());
                            count++;
                        }
                        builder.setLength( 0);
                        builder.append( c);
                        gatheringMode = DIGITS;
                        break;
                    default:
                        assert( false);
                        break;
                }
            } else {
                switch ( gatheringMode) {
                    case DIGITS:
                    case LETTERS:
                        if ( builder.length() > 0) {
                            toAddTo.add( builder.toString());
                            count++;
                        }
                        builder.setLength( 0);
                        builder.append( c);
                        gatheringMode = OTHER;
                        break;
                    case OTHER:
                        builder.append(c);
                        break;
                    default:
                        assert( false);
                        break;
                }
            }
        }
        if ( builder.length() > 0) {
            toAddTo.add( builder.toString());
            count++;
        }
        return count;
    }
}
