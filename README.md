# anonymous2020
source code and test models
This project contains the Triplet region construction (TRC) algorithm. For usage of the TRC simply input a .uai file and the output will be another .uai file containing TRC regions. Users are required to construct the Java application project using the source code BuildTRC_RG.java. The input .uai file has the following constriants:

1. All original factors with factor size >3 are binary factored into triplets, i.e., {X Y Z}, and with singleton and pair-wise factors merged into the tripelts;
2. Assumming all nodes are ordered such that the triplet {X Y Z} is ordered in a way X is always defined before Y, and Z is the child node of X, Y.
3. The output .uai file needs to run on a belief propagation platform supporting the format. In the TRC region graph, nodes id number is the same with the original nodes id number. Nodes id number > original nodes size are intermediate nodes added by the TRC algorithm.


