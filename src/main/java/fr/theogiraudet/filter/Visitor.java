package fr.theogiraudet.filter;

import java.util.List;

/**
 * Représente un visitor (pattern visitor)
 */
public interface Visitor {

    /**
     * @param visitables la liste de tous les visitables à visiter (non null)
     */
    void visitAll(List<? extends Visitable> visitables);

    /**
     * @param parameter l'AccessibilityFilter à visiter (non null)
     */
    void visit(AccessibilityFilter parameter);

    /**
     * @param parameter le TypeFilter à visiter (non null)
     */
    void visit(TypeFilter parameter);

    /**
     * @param parameter le LimitFilter à visiter (non null)
     */
    void visit(LimitFilter parameter);

    /**
     * @param parameter l'OffsetFilter à visiter (non null)
     */
    void visit(OffsetFilter parameter);

    /**
     * @param parameter le LocationFilter à visiter (non null)
     */
    void visit(LocationSorter parameter);

}
