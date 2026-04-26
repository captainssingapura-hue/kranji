// Hand-maintained aggregator. Combines hand-authored + staged records.
package kranji.common.perclass.staging;

import kranji.common.perclass.AllPerclassRecords;
import kranji.common.perclass.promoted.AllPerclassRecordsPromoted;
import kranji.zi.ComposedZiT;

import java.util.ArrayList;
import java.util.List;

/**
 * Combined view of every typed ComposedZi record in the project, across
 * all three lifecycle modules:
 *
 * <ol>
 *   <li>Hand-authored (in {@code kranji-common-perclass}) — the original
 *       canonical records.</li>
 *   <li>Promoted (in {@code kranji-common-perclass-promoted}) — auto-generated
 *       records that have passed staging review.</li>
 *   <li>Staged (in this module) — newest auto-generated records, churn-prone.</li>
 * </ol>
 *
 * <p>Downstream consumers — registry smoke tests, demos, tools — should
 * read from {@link #ALL} so they see all three populations seamlessly.</p>
 *
 * <p>Concatenation order is stable: hand-authored, promoted, staged. Within
 * each, entries retain their own list ordering (alphabetical by FQN).</p>
 */
public final class AllZiRecords {

    private AllZiRecords() {}

    /** Hand-authored ∪ promoted ∪ staged, in that order. Immutable. */
    public static final List<ComposedZiT> ALL;

    static {
        List<ComposedZiT> combined = new ArrayList<>(
                AllPerclassRecords.ALL.size()
                        + AllPerclassRecordsPromoted.ALL.size()
                        + AllPerclassRecordsStaging.ALL.size());
        combined.addAll(AllPerclassRecords.ALL);
        combined.addAll(AllPerclassRecordsPromoted.ALL);
        combined.addAll(AllPerclassRecordsStaging.ALL);
        ALL = List.copyOf(combined);
    }
}
