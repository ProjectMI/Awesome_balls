// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement.logic;

import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import codersafterdark.reskillable.api.requirement.RequirementException;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.logic.impl.XNORRequirement;
import codersafterdark.reskillable.api.requirement.logic.impl.XORRequirement;
import codersafterdark.reskillable.api.requirement.logic.impl.NORRequirement;
import codersafterdark.reskillable.api.requirement.logic.impl.ORRequirement;
import codersafterdark.reskillable.api.requirement.logic.impl.NANDRequirement;
import codersafterdark.reskillable.api.requirement.logic.impl.ANDRequirement;
import codersafterdark.reskillable.api.requirement.logic.impl.NOTRequirement;
import codersafterdark.reskillable.api.requirement.UnobtainableRequirement;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.requirement.Requirement;

public class LogicParser
{
    public static final FalseRequirement FALSE;
    public static final TrueRequirement TRUE;
    
    public static Requirement parseNOT(final String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        return parseNOT(ReskillableAPI.getInstance().getRequirementRegistry().getRequirement(input));
    }
    
    private static Requirement parseNOT(final Requirement requirement) {
        if (requirement == null) {
            return null;
        }
        if (requirement instanceof FalseRequirement || requirement instanceof UnobtainableRequirement) {
            return LogicParser.TRUE;
        }
        if (requirement instanceof TrueRequirement) {
            return LogicParser.FALSE;
        }
        if (requirement instanceof NOTRequirement) {
            return ((NOTRequirement)requirement).getRequirement();
        }
        if (requirement instanceof DoubleRequirement) {
            final DoubleRequirement doubleRequirement = (DoubleRequirement)requirement;
            if (requirement instanceof ANDRequirement) {
                return new NANDRequirement(doubleRequirement.getLeft(), doubleRequirement.getRight());
            }
            if (requirement instanceof NANDRequirement) {
                return new ANDRequirement(doubleRequirement.getLeft(), doubleRequirement.getRight());
            }
            if (requirement instanceof ORRequirement) {
                return new NORRequirement(doubleRequirement.getLeft(), doubleRequirement.getRight());
            }
            if (requirement instanceof NORRequirement) {
                return new ORRequirement(doubleRequirement.getLeft(), doubleRequirement.getRight());
            }
            if (requirement instanceof XORRequirement) {
                return new XNORRequirement(doubleRequirement.getLeft(), doubleRequirement.getRight());
            }
            if (requirement instanceof XNORRequirement) {
                return new XORRequirement(doubleRequirement.getLeft(), doubleRequirement.getRight());
            }
        }
        return new NOTRequirement(requirement);
    }
    
    public static Requirement parseAND(final String input) throws RequirementException {
        final RequirementPair subRequirements = getSubRequirements(input);
        final Requirement left = subRequirements.getLeft();
        final Requirement right = subRequirements.getRight();
        if (left instanceof FalseRequirement || right instanceof FalseRequirement) {
            return LogicParser.FALSE;
        }
        if (left instanceof UnobtainableRequirement || right instanceof UnobtainableRequirement) {
            return new UnobtainableRequirement();
        }
        if (left instanceof TrueRequirement) {
            return right;
        }
        if (right instanceof TrueRequirement) {
            return left;
        }
        final RequirementComparision matches = left.matches(right);
        if (matches.equals(RequirementComparision.EQUAL_TO) || matches.equals(RequirementComparision.GREATER_THAN)) {
            return left;
        }
        if (matches.equals(RequirementComparision.LESS_THAN)) {
            return right;
        }
        return new ANDRequirement(left, right);
    }
    
    public static Requirement parseNAND(final String input) throws RequirementException {
        final RequirementPair subRequirements = getSubRequirements(input);
        final Requirement left = subRequirements.getLeft();
        final Requirement right = subRequirements.getRight();
        if (left instanceof FalseRequirement || right instanceof FalseRequirement || left instanceof UnobtainableRequirement || right instanceof UnobtainableRequirement) {
            return LogicParser.TRUE;
        }
        if (left instanceof TrueRequirement) {
            return parseNOT(right);
        }
        if (right instanceof TrueRequirement) {
            return parseNOT(left);
        }
        if (left.matches(right).equals(RequirementComparision.EQUAL_TO)) {
            return parseNOT(left);
        }
        return new NANDRequirement(left, right);
    }
    
    public static Requirement parseOR(final String input) throws RequirementException {
        final RequirementPair subRequirements = getSubRequirements(input);
        final Requirement left = subRequirements.getLeft();
        final Requirement right = subRequirements.getRight();
        if (left instanceof TrueRequirement || right instanceof TrueRequirement) {
            return LogicParser.TRUE;
        }
        if (left instanceof FalseRequirement) {
            return right;
        }
        if (right instanceof FalseRequirement) {
            return left;
        }
        final RequirementComparision matches = left.matches(right);
        if (matches.equals(RequirementComparision.EQUAL_TO) || matches.equals(RequirementComparision.LESS_THAN)) {
            return left;
        }
        if (matches.equals(RequirementComparision.GREATER_THAN)) {
            return right;
        }
        if (left instanceof NOTRequirement) {
            if (!(right instanceof NOTRequirement) && ((NOTRequirement)left).getRequirement().matches(right).equals(RequirementComparision.EQUAL_TO)) {
                return LogicParser.TRUE;
            }
        }
        else if (right instanceof NOTRequirement && left.matches(((NOTRequirement)right).getRequirement()).equals(RequirementComparision.EQUAL_TO)) {
            return LogicParser.TRUE;
        }
        return new ORRequirement(left, right);
    }
    
    public static Requirement parseNOR(final String input) throws RequirementException {
        final RequirementPair subRequirements = getSubRequirements(input);
        final Requirement left = subRequirements.getLeft();
        final Requirement right = subRequirements.getRight();
        if (left instanceof TrueRequirement && right instanceof TrueRequirement) {
            return LogicParser.FALSE;
        }
        if (left instanceof FalseRequirement) {
            return parseNOT(right);
        }
        if (right instanceof FalseRequirement) {
            return parseNOT(left);
        }
        if (left.matches(right).equals(RequirementComparision.EQUAL_TO)) {
            return parseNOT(left);
        }
        return new NORRequirement(left, right);
    }
    
    public static Requirement parseXOR(final String input) throws RequirementException {
        final RequirementPair subRequirements = getSubRequirements(input);
        final Requirement left = subRequirements.getLeft();
        final Requirement right = subRequirements.getRight();
        if (left instanceof TrueRequirement) {
            return parseNOT(right);
        }
        if (left instanceof FalseRequirement) {
            return right;
        }
        if (right instanceof TrueRequirement) {
            return parseNOT(left);
        }
        if (right instanceof FalseRequirement) {
            return left;
        }
        if (left.matches(right).equals(RequirementComparision.EQUAL_TO)) {
            return LogicParser.FALSE;
        }
        return new XORRequirement(left, right);
    }
    
    public static Requirement parseXNOR(final String input) throws RequirementException {
        final RequirementPair subRequirements = getSubRequirements(input);
        final Requirement left = subRequirements.getLeft();
        final Requirement right = subRequirements.getRight();
        if (left instanceof TrueRequirement) {
            return right;
        }
        if (left instanceof FalseRequirement) {
            return parseNOT(right);
        }
        if (right instanceof TrueRequirement) {
            return left;
        }
        if (right instanceof FalseRequirement) {
            return parseNOT(left);
        }
        if (left.matches(right).equals(RequirementComparision.EQUAL_TO)) {
            return LogicParser.TRUE;
        }
        return new XNORRequirement(left, right);
    }
    
    private static RequirementPair getSubRequirements(final String input) throws RequirementException {
        if (input == null || input.length() < 5 || !input.startsWith("[") || !input.endsWith("]")) {
            throw new RequirementException("Invalid format for double requirement.");
        }
        String first = "";
        int count = 1;
        char lastChar = '[';
        int secondStart = 4;
        int closeBrackets = 0;
        for (int i = 1; i < input.length(); ++i) {
            final char c = input.charAt(i);
            if (lastChar == ']') {
                if (c == ']') {
                    ++closeBrackets;
                }
                else {
                    if (c == '~') {
                        count = count - 1 - closeBrackets;
                    }
                    closeBrackets = 0;
                }
            }
            else {
                if ((lastChar == '|' || lastChar == '~') && c == '[') {
                    ++count;
                }
                closeBrackets = 0;
            }
            if (count == 0) {
                if (!first.isEmpty()) {
                    throw new RequirementException("Something went wrong, double check brackets.");
                }
                first = input.substring(1, i - 1);
                secondStart = i + 2;
            }
            lastChar = c;
        }
        count = count - 1 - closeBrackets;
        if (count != 0) {
            throw new RequirementException("Mismatched brackets.");
        }
        final RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        final Requirement left = registry.getRequirement(first);
        if (left == null) {
            throw new RequirementException("Invalid left-hand requirement '" + first + "'.");
        }
        final String second = input.substring(secondStart, input.length() - 1);
        final Requirement right = registry.getRequirement(second);
        if (right == null) {
            throw new RequirementException("Invalid right-hand requirement '" + second + "'.");
        }
        return new RequirementPair(left, right);
    }
    
    static {
        FALSE = new FalseRequirement();
        TRUE = new TrueRequirement();
    }
    
    private static class RequirementPair
    {
        private Requirement left;
        private Requirement right;
        
        private RequirementPair(final Requirement left, final Requirement right) {
            this.left = left;
            this.right = right;
        }
        
        private Requirement getLeft() {
            return this.left;
        }
        
        private Requirement getRight() {
            return this.right;
        }
    }
}
