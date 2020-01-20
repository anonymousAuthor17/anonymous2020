# anonymous2020

This project contains the Triplet region construction (TRC) algorithm's source code and test models. For usage of the TRC simply input a xxx_ordered.uai file which contains the parent-child CPDs and the pre-defined parent-child ordering (see constriants below). The output file will be another .uai file containing TRC regions. Copy the TRC regions and add these regions to the original xxx.uai file. Then, run it using the Cluster Variation Method on a belief propagation platform which supports the .uai format.

Users are required to construct the Java application project using the source code BuildTRC_RG.java. The input .uai file has the following constriants:

1. All original factors with factor size >3 are binary factored into triplets, i.e., {X Y Z};
2. The parent-child ordering is pre-defined such that in the triplet {X Y Z}, X is always defined before Y, and Z is the child node of X, Y. This means if {a b X} and {c d Y} are CPDs defining X and Y respectively, {a b X} is always listed before {c d Y} in the .uai file.
3. The output .uai file needs to run on a belief propagation platform supporting the format. In the TRC region graph, nodes id number is the same with the original nodes id number. Nodes id number > original nodes size are intermediate nodes added by the TRC algorithm.


