/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildtrc_rg;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author anonymousAuthor17
 */
public class BuildTRC_RG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      
      new BuildTRC_RG();
    }
     public BuildTRC_RG() {
    
         try{// input file:
         // assumming all nodes are ordered such that the triplet {X Y Z} is ordered in a way X is always defined before Y, and
         //Z is the child node
             
              //    BufferedReader bf2= new BufferedReader(new FileReader("promedas_ordered.uai"));// input ordered npt file
                BufferedReader bf2= new BufferedReader(new FileReader("pedigree_ordered.uai"));
              // BufferedReader bf2= new BufferedReader(new FileReader("asia_ordered.uai"));
              // BufferedReader bf2= new BufferedReader(new FileReader("student_ordered.uai"));
              //   BufferedReader bf2= new BufferedReader(new FileReader("bayesgrid_ordered.uai"));
            //   BufferedReader bf2= new BufferedReader(new FileReader("HMM_ordered.uai"));
        ///////////////////////////////////////////////////////////////////////////////////////////////input card file
                  
                  BufferedReader bf3= new BufferedReader(new FileReader("pedigree_card.uai"));// cardinals
                  
                  
             /////////////////////////////////////////////////////////////////////////////////////////     
       
       
        boolean node_reuse=true;// perform node reuse for replicated nodes
        boolean inter_triplet_removal=true;// perform interaction triplet removal = relaxation of the perfect correlation property
        
         String s2 = null, s3=null;
        List<String> reusednodes=new ArrayList();// if a child original node is reused once it should not be reused twice in another Hamiltonian path
        
        List<String> copiedNPTsID=new ArrayList(); // save the IDs of the primarytriplet which has NPT 1 0 1 0 0 1 0 1
        List<String> card=new ArrayList();//save cardinals for all nodes
        int ID=0;
        Map indexmap=new HashMap();// 
        List <String> mnodes=new ArrayList();
      
        Map indexmapr=new HashMap();
     
        List<String[]> npts=new ArrayList();
     
        while((s2 = bf2.readLine())!=null){//
            if(s2.length()!=0){
            String [] pots=s2.split(" ");
         
            mnodes.add(pots[pots.length-1]);
            
            npts.add(pots);// save NPT
             
            
            }
        }
         ///////
        List <String []> tempcard=new ArrayList();
   while((s3 = bf3.readLine())!=null){//
            if(s3.length()!=0){
            String [] pots=s3.split(" ");
         
            tempcard.add(pots);
            
            
            }
        }
        
   for (int k=0;k<tempcard.size();k++){
      
         
       for (int w=0;w<tempcard.get(k).length;w++) card.add(tempcard.get(k)[w]);
   
   
   
   }
   
   //for (int j=0;j<card.size();j++)System.out.print(card.get(j)+" ");
   //System.out.println("card size="+card.size());
   
         ///////
            int c=1; //int sizecopies=0;
         
         System.out.println("original nodes size:"+mnodes.size());
         System.out.println("card size="+card.size());
         for (int t=0;t<mnodes.size();t++){
           //  System.out.println(mnodes.get(t));
             
            indexmap.put(mnodes.get(t), "x"+t);
            indexmapr.put("x"+t, mnodes.get(t));// x0 to x_(size-1) as the original node
         
          //   System.out.println("x"+t+" "+mnodes.get(t));
         
         }
         
         //build the primary triplets and interaction tripelts by BFG mapping
         List<String []> primarytriplets=new ArrayList();
         List<String []> interactiontriplets=new ArrayList();
         
         System.out.println("npts size:"+npts.size());
         
         
         
         int Enodesize=mnodes.size();// the number of E nodes to add
         
         
         for (int t=0;t<npts.size();t++){ // the ordered NPTs
         
         
            if (t==0 || t==1){} // x0 and x1 will be merged into {x0 x1 x2}
            
            else{
            
                String [] npt=npts.get(t); // build from t==2
                
                if (npt[0].equals("1")) {
                
                  //  System.out.println(npt[0]+" "+npt[1]);
                    
                    if (t==2){ String [] str=new String [3]; str[0]="x0";str[1]="x1";str[2]="x2"; // no moral// {x0 x1 x2}
                                if (!areallexisted(primarytriplets,str))
                                { primarytriplets.add(str); ID++;}
                    }else{
                    
                        String [] str=new String [3]; 
                        
                       
                        
                        str[2]=indexmap.get(npt[1]).toString(); //moral {str[0] str[1]}
                        String [] temp=str[2].split("x");
                       // System.out.println("test "+temp[1]);
                        str[1]="x"+(Integer.valueOf(temp[1])-1);
                        
                         if (!node_reuse) {
                         str[0]="E"+Enodesize;   
                         Enodesize++;//increase the size
                         }else                             
                            str[0]=str[1];
                       
                        if (!areallexisted(primarytriplets,str))
                            { primarytriplets.add(str); ID++;}
                       // System.out.println(str[0]+" "+str[1]+" "+str[2]);
                       
                        String [] str1=new String [3]; str1[0]="x"+(Integer.valueOf(temp[1])-2); str1[1]=str[0]; str1[2]=str[1];
                        if (!areallexisted(interactiontriplets,str1))
                            interactiontriplets.add(str1);
                        //System.out.println(str1[0]+" "+str1[1]+" "+str1[2]);
                  
                    
                    }
                
                }
                  
                if (npt[0].equals("2")){
                
                   // System.out.println(npt[0]+" "+npt[1]+" "+npt[2]);
                 if (t==2){ String [] str=new String [3]; str[0]="x0";str[1]="x1";str[2]="x2"; // no moral// {x0 x1 x2}
                    if (!areallexisted(primarytriplets,str))
                        { primarytriplets.add(str); ID++;}
                    
                    
                    }else{
                 
                 
                     int distance=Integer.valueOf(indexmap.get(npt[2]).toString().substring(1, indexmap.get(npt[2]).toString().length()))-
                             Integer.valueOf(indexmap.get(npt[1]).toString().substring(1,indexmap.get(npt[1]).toString().length()));// calculate the distance of the two nodes
                     
                    // System.out.println("distance="+distance);
                     
                     if (distance==1){// just add one primary and one interaction triplet
                     
                         String [] str= new String [3]; 
                         
                         if (!node_reuse){
                          str[0]="E"+Enodesize; 
                          Enodesize++;//increase the size
                          } else 
                             str[0]=indexmap.get(npt[1]).toString(); // 
                         
                         str[1]=indexmap.get(npt[1]).toString(); 
                         str[2]=indexmap.get(npt[2]).toString();
                       
                        if (!areallexisted(primarytriplets,str))
                            { primarytriplets.add(str); ID++;}
                       // System.out.println(str[0]+" "+str[1]+" "+str[2]);
                        String []str1=new String [3]; str1[0]="x"+(Integer.valueOf(indexmap.get(npt[2]).toString().substring(1, indexmap.get(npt[2]).toString().length()))-2);
                        str1[1]=str[0];
                        str1[2]=str[1];
                        if (!areallexisted(interactiontriplets,str1))
                            interactiontriplets.add(str1);
                        //System.out.println(str1[0]+" "+str1[1]+" "+str1[2]);
                       
                     }else{// distance >=2
                     
                   //  System.out.println("distance:"+distance);
                     
                           
                     if (indexmap.get(npt[1]).toString().equals("x0")  ){
                     
                       
                          int copies=distance-2;
                          
                          String [] str=new String [3]; 
                          str[0]=indexmap.get(npt[1]).toString();//one primary
                          str[2]=indexmap.get(npt[2]).toString();
                          str[1]="x"+(Integer.valueOf(str[2].substring(1, str[2].length()))-1);
                          
                          if (!areallexisted(primarytriplets,str))
                              { primarytriplets.add(str); ID++;}
                          
                         // List <String []> teminteractionadded=new ArrayList();
                          String [] record=new String [3];;
                          for (int p=0;p<copies;p++){ // number of copied nodes
                          
                          
                              String str1[]=new String [3];
                              
                              str1[1]=str[0];
                              str1[2]="x"+(Integer.valueOf(str[1].substring(1, str[1].length()))-p);
                              str1[0]="x"+(Integer.valueOf(str1[2].substring(1, str1[2].length()))-1);
                          if (str1[0].equals("x1")) str1[0]="x0";
                          
                          if (inter_triplet_removal){
                          
                              if (copies>2){
                                  
                                  if (p==0) { if (!areallexisted(interactiontriplets,str1))
                                              interactiontriplets.add(str1);}
                                  if (p==1) {record[0]=str1[0];record[1]=str1[1];record[2]=str1[2];}
                                  if (p==copies-1){
                                  
                                        String [] addstr=new String [3];
                                        addstr[0]=record[2];
                                        addstr[1]=str1[1];
                                        addstr[2]=str1[2];
                                        if (!areallexisted(interactiontriplets,addstr))
                              interactiontriplets.add(addstr); 
                             //     System.out.println("testtesttest");
                                  }
                                  
                              
                              }else{
                              
                               if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);
                              
                              }
                          
                          
                          }else{
                          
                          if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);                           
                          }
                          
                          
                          }// end for copies
                          
                          
                         //  sizecopies+=copies;
                          
                     
                     }else{
                     
                     
                         int copies=distance-1;
                         
                         
                     String [] str=new String [3]; str[0]=indexmap.get(npt[1]).toString();//one primary
                          str[2]=indexmap.get(npt[2]).toString();
                          str[1]="x"+(Integer.valueOf(str[2].substring(1, str[2].length()))-1);
                     if (!areallexisted(primarytriplets,str))
                         { primarytriplets.add(str); ID++;}
                     
                     //System.out.println("test:"+str[0]+" "+str[1]+" "+str[2]);
                     String []record=new String [3];;
                         for (int p=0;p<copies;p++){ // number of copied nodes
                          
                          String str1[]=new String [3];
                              
                              str1[1]=str[0];
                              str1[2]="x"+(Integer.valueOf(str[1].substring(1, str[1].length()))-p);
                              str1[0]="x"+(Integer.valueOf(str1[2].substring(1, str1[2].length()))-1);
                          //if (str1[0].equals("x1")) str1[0]="x0";
                          if (inter_triplet_removal){
                          
                              if (copies>2){
                                  
                                  if (p==0) { if (!areallexisted(interactiontriplets,str1))
                                              interactiontriplets.add(str1);}
                                  if (p==1) {record[0]=str1[0];record[1]=str1[1];record[2]=str1[2];}
                                  if (p==copies-1){
                                  
                                        String [] addstr=new String [3];
                                        addstr[0]=record[2];
                                        addstr[1]=str1[1];
                                        addstr[2]=str1[2];
                                        if (!areallexisted(interactiontriplets,addstr))
                              interactiontriplets.add(addstr); 
                              //    System.out.println("testtesttest");
                                  }
                                  
                              
                              }else{
                              
                               if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);
                              
                              }
                          
                          
                          }else{
                          
                          if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);                           
                          }
                    
                          
                          }// end for
                      //sizecopies+=copies;
                     }
                     
                     } // end distance >2
                
                 
                 }
                    
                
                } // end npt[0] equals 2
            
                if (npt[0].equals("3")){
                
                
                  //  System.out.println(npt[0]+" "+npt[1]+" "+npt[2]+" "+npt[3]);
                
                    if (t==2){ String [] str=new String [3]; str[0]="x0";str[1]="x1";str[2]="x2"; // no moral// {x0 x1 x2}
                    if (!areallexisted(primarytriplets,str))
                       { primarytriplets.add(str); ID++;}
                    
                    
                    }else{
                    
                    
                    
                        int distance12=Integer.valueOf(indexmap.get(npt[3]).toString().substring(1, indexmap.get(npt[3]).toString().length()))-
                             Integer.valueOf(indexmap.get(npt[2]).toString().substring(1,indexmap.get(npt[2]).toString().length()));// calculate the distance of the two nodes
                     
                        
                        int distance02=Integer.valueOf(indexmap.get(npt[3]).toString().substring(1, indexmap.get(npt[3]).toString().length()))-
                             Integer.valueOf(indexmap.get(npt[1]).toString().substring(1,indexmap.get(npt[1]).toString().length()));// calculate the distance of the two nodes
                     
                        
                    if (distance12==1){ // no sink // equivalent to two nodes case
                    
                         
                        if (indexmap.get(npt[1]).toString().equals("x0")  ){
                     
                         // distance=distance-1;
                          int copies=distance02-2;
                          
                          String [] str=new String [3]; 
                          str[0]=indexmap.get(npt[1]).toString();//one primary
                           if (!isexist(npt[1].toString(),reusednodes)) reusednodes.add(npt[1]);
                        //  System.out.println(npt[1]);
                          
                          str[2]=indexmap.get(npt[3]).toString();
                          str[1]="x"+(Integer.valueOf(str[2].substring(1, str[2].length()))-1);
                          
                          if (!areallexisted(primarytriplets,str))
                             { primarytriplets.add(str); ID++;}
                          
                          
                          String [] record=new String [3];;
                          for (int p=0;p<copies;p++){ // number of copied nodes
                          
                          
                              String str1[]=new String [3];
                              
                              str1[1]=str[0];
                              str1[2]="x"+(Integer.valueOf(str[1].substring(1, str[1].length()))-p);
                              str1[0]="x"+(Integer.valueOf(str1[2].substring(1, str1[2].length()))-1);
                          if (str1[0].equals("x1")) str1[0]="x0";
                          if (inter_triplet_removal){
                          
                              if (copies>2){
                                  
                                  if (p==0) { if (!areallexisted(interactiontriplets,str1))
                                              interactiontriplets.add(str1);}
                                  if (p==1) {record[0]=str1[0];record[1]=str1[1];record[2]=str1[2];}
                                  if (p==copies-1){
                                  
                                        String [] addstr=new String [3];
                                        addstr[0]=record[2];
                                        addstr[1]=str1[1];
                                        addstr[2]=str1[2];
                                        if (!areallexisted(interactiontriplets,addstr))
                              interactiontriplets.add(addstr); 
                              //    System.out.println("testtesttest");
                                  }
                                  
                              
                              }else{
                              
                               if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);
                              
                              }
                          
                          
                          }else{
                          
                          if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);                           
                          }
                          
                          }
                          
                        //  sizecopies+=copies;
                     
                     }else{
                     
                     
                         int copies=distance02-1;
                         
                         
                          String [] str=new String [3]; 
                          str[0]=indexmap.get(npt[1]).toString();//one primary
                          if (!isexist(npt[1].toString(),reusednodes)) reusednodes.add(npt[1]); // avoid breaking other path
                        //  System.out.println(npt[1]);
                          
                          str[2]=indexmap.get(npt[3]).toString();
                          str[1]="x"+(Integer.valueOf(str[2].substring(1, str[2].length()))-1);
                   
                          if (!areallexisted(primarytriplets,str))
                             { primarytriplets.add(str); ID++;}
                     
                     //System.out.println("test:"+str[0]+" "+str[1]+" "+str[2]);
                          String [] record=new String [3];;
                         for (int p=0;p<copies;p++){ // number of copied nodes
                          
                              String str1[]=new String [3];
                              
                              str1[1]=str[0];
                              str1[2]="x"+(Integer.valueOf(str[1].substring(1, str[1].length()))-p);
                              str1[0]="x"+(Integer.valueOf(str1[2].substring(1, str1[2].length()))-1);
                          //if (str1[0].equals("x1")) str1[0]="x0";
                              
                          if (inter_triplet_removal){
                          
                              if (copies>2){
                                  
                                  if (p==0) { if (!areallexisted(interactiontriplets,str1))
                                              interactiontriplets.add(str1);}
                                  if (p==1) {record[0]=str1[0];record[1]=str1[1];record[2]=str1[2];}
                                  if (p==copies-1){
                                  
                                        String [] addstr=new String [3];
                                        addstr[0]=record[2];
                                        addstr[1]=str1[1];
                                        addstr[2]=str1[2];
                                        if (!areallexisted(interactiontriplets,addstr))
                                            interactiontriplets.add(addstr); 
                                //  System.out.println("testtesttest");
                                  }
                                  
                              
                              }else{
                              
                               if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);
                              
                              }
                          
                          
                          }else{
                          
                          if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);                           
                          }
                           
                          
                          }// end for
                     // sizecopies+=copies;
                        
                     }
                        
                    
                    
                    }else{// sink
                    
                    
                        
                        
                        int sink=distance12-1;// the number of copies of npt[3]
                        
                        
                        if (indexmap.get(npt[1]).toString().equals("x0")  ){
                        
                       // System.out.println("test run x0");
                            int copies=distance02-2-sink;
                            
                          String [] str=new String [3]; 
                          str[0]=indexmap.get(npt[1]).toString();//one primary
                          
                          str[1]=indexmap.get(npt[2]).toString();
                          
                          
                         //  if (!node_reuse)  
                           {  
                           str[2]="E"+Enodesize;// copy of npt[3]
                         
                           Enodesize++;
                           }
                  
                          
                          if (!areallexisted(primarytriplets,str))
                             { primarytriplets.add(str); ID++;}  // an primary triplet copying original npt
                            
                          String [] record=new String [3];;
                           for (int p=0;p<copies;p++){
                           
                           
                           String str1[]=new String [3];
                           
                              str1[1]=str[0];//npt[1]
                              str1[2]="x"+(Integer.valueOf(str[1].substring(1, str[1].length()))-p);// from npt[2] node to low dimensions
                              str1[0]="x"+(Integer.valueOf(str1[2].substring(1, str1[2].length()))-1);
                          if (str1[0].equals("x1")) str1[0]="x0";
                           if (inter_triplet_removal){
                          
                              if (copies>2){
                                  
                                  if (p==0) { if (!areallexisted(interactiontriplets,str1))
                                              interactiontriplets.add(str1);}
                                  if (p==1) {record[0]=str1[0];record[1]=str1[1];record[2]=str1[2];}
                                  if (p==copies-1){
                                  
                                        String [] addstr=new String [3];
                                        addstr[0]=record[2];
                                        addstr[1]=str1[1];
                                        addstr[2]=str1[2];
                                        if (!areallexisted(interactiontriplets,addstr))
                              interactiontriplets.add(addstr); 
                             //     System.out.println("testtesttest");
                                  }
                                  
                              
                              }else{
                              
                               if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);
                              
                              }
                          
                          
                          }else{
                          
                          if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);                           
                          }
                           
                           } // end for
                            // sizecopies+=copies;
                          //add sink triplets// interaction triplets
                           
                         
                            String str3[]=new String [3];
                          str3[0]=str[2];
                                  
                          str3[2]= indexmap.get(npt[3]).toString(); 
                          str3 [1]=  "x"+(Integer.valueOf(str3[2].substring(1, str3[2].length()))-1); 
                           
                           if (!areallexisted(primarytriplets,str3)) // 1 0 1 0 0 1 0 1
                          { primarytriplets.add(str3); 
                            copiedNPTsID.add(String.valueOf(ID));
                            ID++;
                         // System.out.println("primary:"+str[0]+" "+str[1]+" "+str[2]);
                          }
                           
                           for (int p=0;p<sink;p++){
                          
                         
                           
                           String str2[]=new String[3];
                           str2[0]="x"+(Integer.valueOf(str[1].substring(1,str[1].length()))+p);//npt[2]
                           str2[1]=str[2];//npt[3]
                           str2[2]="x"+(Integer.valueOf(str2[0].substring(1,str2[0].length()))+1);
                           
                           if (!areallexisted(interactiontriplets,str2))
                               interactiontriplets.add(str2);
                           }
                        
                           // sizecopies+=copies;
                            
                           npt[3]=str[2].substring(1, str[2].length());//replace the original node with the E node
                           
                        }else{
                        
                      //  System.out.println("test sink:"+npt[1]+" "+npt[2]+" "+npt[3]);
                            int copies =distance02-1-sink;
                        
                          String [] str=new String [3]; 
                         // str[0]="E"+Enodesize;//one primary//copy of npt[1]
                          str[0]=indexmap.get(npt[1]).toString();//npt[1]
                        
                          str[1]=indexmap.get(npt[2]).toString();
                         
                      // if (!node_reuse)  {  
                           str[2]="E"+Enodesize;// copy of npt[3]
                        
                            Enodesize++;
                        //}
                       
                            
                          if (!areallexisted(primarytriplets,str))// copying original NPT
                          { primarytriplets.add(str); ID++;
                         // System.out.println("primary:"+str[0]+" "+str[1]+" "+str[2]);
                          }
                          
                         String [] record=new String [3]; 
                        for (int p=0;p<copies;p++){
                           
                           
                           String str1[]=new String [3];
                           
                              str1[1]=str[0];//npt[1]
                              str1[2]="x"+(Integer.valueOf(str[1].substring(1, str[1].length()))-p);// from npt[2] node to low dimensions
                              str1[0]="x"+(Integer.valueOf(str1[2].substring(1, str1[2].length()))-1);
                         // if (str1[0].equals("x1")) str1[0]="x0";
                              
                           if (inter_triplet_removal){
                          
                              if (copies>2){
                                  
                                  if (p==0) { if (!areallexisted(interactiontriplets,str1))
                                              interactiontriplets.add(str1);}
                                  if (p==1) {record[0]=str1[0];record[1]=str1[1];record[2]=str1[2];}
                                  if (p==copies-1){
                                  
                                        String [] addstr=new String [3];
                                        addstr[0]=record[2];
                                        addstr[1]=str1[1];
                                        addstr[2]=str1[2];
                                        if (!areallexisted(interactiontriplets,addstr))
                              interactiontriplets.add(addstr); 
                                //  System.out.println("testtesttest");
                                  }
                                  
                              
                              }else{
                              
                               if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);
                              
                              }
                          
                          
                          }else{
                          
                          if (!areallexisted(interactiontriplets,str1))
                              interactiontriplets.add(str1);                           
                          }
                           
                           
                          } // end for copies
                          
                       // sizecopies+=copies;
                          
                          // add sink triplets
                         String str3[]=new String [3];
                          str3[0]=str[2];
                                  
                          str3[2]= indexmap.get(npt[3]).toString(); 
                          str3[1]=  "x"+(Integer.valueOf(str3[2].substring(1, str3[2].length()))-1); 
                           
                           if (!areallexisted(primarytriplets,str3)) //  1 0 1 0 0 1 0 1
                          { primarytriplets.add(str3); 
                            copiedNPTsID.add(String.valueOf(ID));
                            ID++;
                         // System.out.println("primary:"+str3[0]+" "+str3[1]+" "+str3[2]);
                          }
                           
                           
                         for (int p=0;p<sink;p++){
                           
                           String str2[]=new String[3];
                           str2[0]="x"+(Integer.valueOf(str[1].substring(1,str[1].length()))+p);//npt[2]
                           str2[1]=str[2];//copy of npt[3]
                           str2[2]="x"+(Integer.valueOf(str2[0].substring(1,str2[0].length()))+1);
                           
                          if (!areallexisted(interactiontriplets,str2))
                          { interactiontriplets.add(str2);
                            //System.out.println("sink:"+str2[0]+" "+str2[1]+" "+str2[2]);
                          
                          }
                          }// end for
                        
                        // System.out.println("size copy:"+sink);
                         
                         
                         npt[3]=str[2].substring(1, str[2].length());//replace the original node with the E node
                         
                         
                        }
                        
                       
                    
                    }// end sink
                   
                    }
                    
                
                } // end npt[0] equals 3
            }
         
         
         
         }// end for npts
         ///////////
       //  System.out.println("number of copies:"+sizecopies);
          List<String> allnodes=new ArrayList();
     
      
      for (int k=0;k<primarytriplets.size();k++){ // all primary triplets npts
      String [] str=primarytriplets.get(k);
     // System.out.println(str[0]+" "+str[1]+" "+str[2]);
     // System.out.println();
      
          String str1=str[0];
          String str2=str[1];
          String str3=str[2];
          
          if (!isexist(str1,allnodes)){ allnodes.add(str1); //if (!str1.contains("m")) netnnode++;
          }
          if (!isexist(str2,allnodes)) { allnodes.add(str2);//if (!str2.contains("m")) netnnode++;
          }
          if (!isexist(str3,allnodes)) {allnodes.add(str3);//if (!str3.contains("m")) netnnode++;
          }
          
      }
         
        Map namemap=new HashMap();
     
      
     
      for (int k=0;k<allnodes.size();k++){   
            
          String str=allnodes.get(k);
          if (str.contains("E"))
          
            namemap.put(str, str.substring(1, str.length()));
            //allnodesordered.add(k);
          
          else namemap.put(str, indexmapr.get(str));
      } 
         
         ///////simply the interaction triplets
           
           List<String []> interactiontriplets2=new ArrayList(); 
           
           for (int t=0;t<interactiontriplets.size();t++){
           
              String  [] triple=interactiontriplets.get(t);
              
              if (!areallexisted(primarytriplets, triple)) interactiontriplets2.add(triple); // 
           
           
           
           }
         
           
           //simply the primary triplets
           List<String []> primarytriplets2=new ArrayList(); 
           
            for (int t=0;t<primarytriplets.size();t++){
           
              String  [] triple=primarytriplets.get(t);
               
              if (triple[0].equals(triple[1])){
              
                 String [] temp=new String [2];
                 temp[0]=triple[0]; temp[1]=triple[2];
                 
                 primarytriplets2.add(temp);
              
              } else if (triple[0].equals(triple[2])){
              
                 String [] temp=new String [2];
                 temp[0]=triple[0]; temp[1]=triple[1];
                 
                 primarytriplets2.add(temp);
              
              }  else if (triple[1].equals(triple[2])){
              
                 String [] temp=new String [2];
                 temp[0]=triple[0]; temp[1]=triple[1];
                 
                 primarytriplets2.add(temp);
              
              }  else primarytriplets2.add(triple);
               
              
              
           
           
           }
         
         //////////////////////////////////// remove the same primary triplets to the npts
            
            for (int k=0;k<primarytriplets2.size();k++){
            
                 String [] triplet=primarytriplets2.get(k);
                 
                 if (triplet.length==3){
                 
                     if (areallexistedinNPT(npts,triplet,namemap)){// if true then make it empty
                     
                           
                         triplet[0]="";
                         triplet[1]="";
                         triplet[2]="";
                     
                     }
                 
                 }
                 
                  if (triplet.length==2){
                 
                     if (areallexistedinNPT(npts,triplet,namemap))
                     {// if true then make it empty
                     
                           
                         triplet[0]="";
                         triplet[1]="";
                         //triplet[2]="";
                     
                     }
                 
                 }
            
            
            
            
            }
            ////////////////////////////
            
            int primarytripletsize=0;
            for (int h=0;h<primarytriplets2.size();h++){
            
            String [] triplet=primarytriplets2.get(h);
            
            if (!triplet[0].equals("")) primarytripletsize++;
            
            
            }
            
            ////////////////////////////
       System.out.println("printing original NPTs with some original nodes replaced by added nodes:");
       System.out.println();
       
            
            for (int f=0;f<npts.size();f++){
            
            String [] npt=npts.get(f);
            
            for (int h=0;h<npt.length;h++){
            System.out.print(npt[h]+" ");
            
            }
            System.out.println();
            
            }
            
         System.out.println();
         System.out.println("primary triplets in the BFG:"+primarytripletsize);
         System.out.println("interaction triplets in the BFG:"+interactiontriplets2.size());
         System.out.println("all TRC outer regions:"+(primarytripletsize+interactiontriplets2.size()));
         
          
      
      
          System.out.println("allnodes:"+allnodes.size());
          System.out.println();
          System.out.println("MARKOV");
          System.out.println(allnodes.size());
           for (int i=0;i<allnodes.size();i++){
           
           System.out.print("2 ");
       
           }
           
           System.out.println();
           System.out.println();
           System.out.println((primarytripletsize+interactiontriplets2.size())+npts.size());
           System.out.println();
           System.out.println();
           System.out.println();
           System.out.println();
           for (int i=0;i<mnodes.size();i++){
           
           //System.out.println(mnodes.get(i));
           
           }
           
         
           
           
      
      
      String [] p_triplet=null; 
           for (int u=0;u<primarytriplets2.size();u++){
      
        p_triplet=primarytriplets2.get(u);
        
        if (p_triplet.length==2) {
            if (!p_triplet[0].equals(""))
            System.out.println("2 "+namemap.get(p_triplet[0])+" "+namemap.get(p_triplet[1])+" " ); }
        
       else {
            
            if (!p_triplet[0].equals(""))
            System.out.println("3 "+namemap.get(p_triplet[0])+" "+namemap.get(p_triplet[1])+" "+namemap.get(p_triplet[2])+" ");
     
        }
      }
           
           
           System.out.println();
           
           
           ////////////////////
           for (int u=0;u<interactiontriplets2.size();u++)
           {
      
      String [] i_triplet=interactiontriplets2.get(u);
    System.out.println("3 "+namemap.get(i_triplet[0])+" "+namemap.get(i_triplet[1])+" "+namemap.get(i_triplet[2])+" ");
      
  
       }
           ////
           
           System.out.println();
           System.out.println();
           
            
           for (int u=0;u<primarytriplets2.size();u++){
           
               p_triplet=primarytriplets2.get(u);
               
               if (copiedNPTsID.size()!=0){
               if (p_triplet.length==3){
                   
               for (int p=0;p<copiedNPTsID.size();p++){
               
                   if (u==Integer.valueOf(copiedNPTsID.get(p))){
                   
                   System.out.println("8 1 0 1 0 0 1 0 1");
                   break;
                   }
               
                 if (p==copiedNPTsID.size()-1 && !p_triplet[0].equals(""))  System.out.println("8 1 1 1 1 1 1 1 1");    
                   
               }
               
               }else {
                   if (!p_triplet[0].equals(""))
                   System.out.println("4 1 1 1 1");}
               
               }else{
               
               if (p_triplet.length==3 ){
               
                if (!p_triplet[0].equals(""))   // not empty
               System.out.println("8 1 1 1 1 1 1 1 1");
               
               
               }else {
                   
                   if (!p_triplet[0].equals(""))
                   System.out.println("4 1 1 1 1");}
               
               
           }
           
           }
          
           
        for (int u=0;u<interactiontriplets2.size();u++){
        
        System.out.println("8 1 1 1 1 1 1 1 1"); 
        
        }
                   
          
           
         }catch(Exception e){}
    
    
    }
    
    
    
     private boolean isexist(String str, List lst){
  
       boolean flag=false;
       
       for (int i=0;i<lst.size();i++){
       
            
           String str3=lst.get(i).toString();
       
           if (str3.equals(str)) {
           
           flag=true;
           break;
           
           }
       
       }
  
  
  return flag;
  }
     private boolean areallexisted(List<String[]> list, String [] npt){
     
         boolean flag=false;
         
        for (int j=0;j<list.size();j++){
         
              String [] npt0=list.get(j);
         
         
                  if (npt[0].equals(npt0[0]) || npt[0].equals(npt0[1]) || npt[0].equals(npt0[2])){
                  
                       if (npt[1].equals(npt0[0]) || npt[1].equals(npt0[1]) || npt[1].equals(npt0[2])){
                        
                          if (npt[2].equals(npt0[0]) || npt[2].equals(npt0[1]) || npt[2].equals(npt0[2])){
                          
                            
                            return true; 
                          }
                       
                       }
                  
                  
                  }
         
         
        }
     
        return flag;
        
     }
     
     private boolean areallexistedinNPT(List<String[]> list, String [] npt, Map namemap){// 
     
         boolean flag=false;
         String [] tempnpt=new String [3];
         tempnpt[0]=namemap.get(npt[0]).toString();
         tempnpt[1]=namemap.get(npt[1]).toString();
         if (npt.length==3)
         tempnpt[2]=namemap.get(npt[2]).toString();
         
        for (int j=0;j<list.size();j++){
         
              String [] npt0=list.get(j);// original factors
         
              if (npt0.length>3){ // only consider triplets
         
                  if (tempnpt[0].equals(npt0[1]) || tempnpt[0].equals(npt0[2]) || tempnpt[0].equals(npt0[3])){
                  
                       if (tempnpt[1].equals(npt0[1]) || tempnpt[1].equals(npt0[2]) || tempnpt[1].equals(npt0[3])){
                           
                         if (npt.length==2) return true;
                        
                          if (tempnpt[2].equals(npt0[1]) || tempnpt[2].equals(npt0[2]) || tempnpt[2].equals(npt0[3])){
                          
                            
                            return true; 
                          }
                       
                       }
                  
                  
                  }
        } 
              
              
              
         
        }
     
        return flag;
        
     }
     
     
}