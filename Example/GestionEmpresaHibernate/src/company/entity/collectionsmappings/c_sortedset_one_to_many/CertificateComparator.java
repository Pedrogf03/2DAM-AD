package company.entity.collectionsmappings.c_sortedset_one_to_many;

import java.util.Comparator;

/**
 * <p>If we use sort="natural" setting then we do not need to create a separate
 * class because Certificate class already has implemented Comparable interface
 * and hibernate will use compareTo() method defined in Certificate class to
 * compare certificate names. But we are using a custom comparator CertificateComparator
 * in our mapping file so we would have to create this class based on our
 * sorting algorithm. Let us do descending sorting in this class using this class.
 */
public class CertificateComparator implements Comparator<Certificate> {
  public int compare(Certificate o1, Certificate o2) {
    final int BEFORE = -1;
    final int AFTER = 1;

    /* To reverse the sorting order, multiple by -1 */
    if (o2 == null) {
      return BEFORE * -1;
    }
    String thisCertificate = o1.getName();
    String thatCertificate = o2.getName();
    if (thisCertificate == null) {
      return AFTER * 1;
    } else if (thatCertificate == null) {
      return BEFORE * -1;
    } else {
      return thisCertificate.compareTo(thatCertificate) * -1;
    }
  }
}