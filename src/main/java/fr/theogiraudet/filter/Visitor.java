package fr.theogiraudet.filter;

import java.util.List;

/**
 * Représente un visitor (pattern visitor)
 */
public interface Visitor {

    void visitAll(List<? extends Visitable> visitables);

    /**
     * @param parameter l'AccessibilityFilter à visiter
     */
    void visit(AccessibilityFilter parameter);

    /**
     * @param parameter le TypeFilter à visiter
     */
    void visit(TypeFilter parameter);

    /**
     * @param parameter le LimitFilter à visiter
     */
    void visit(LimitFilter parameter);

    /**
     * @param parameter l'OffsetFilter à visiter
     */
    void visit(OffsetFilter parameter);

    /**
     * @param parameter le LocationFilter à visiter
     */
    void visit(LocationSorter parameter);

}
