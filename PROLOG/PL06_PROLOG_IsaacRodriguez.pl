/**store( StoreName,  ListEmployees, ListSmoothies)
*ListSmoothies(SmoothieName, ListFruits, Price)    
*/

store(best_smoothies, [alan,john,mary],        
      [smoothie(berry, [orange, blueberry, strawberry], 2),
       smoothie(tropical, [orange, banana, mango, guava], 3),         
       smoothie(blue, [banana, blueberry], 3) ]). 

store(all_smoothies, [keith,mary],        
      [smoothie(pinacolada, [orange, pineapple, coconut], 2),         
       smoothie(green, [orange, banana, kiwi], 5),        
       smoothie(purple, [orange, blueberry, strawberry], 2),          
       smoothie(smooth, [orange, banana, mango],1) ]).  

store(smoothies_galore, [heath,john,michelle],        
      [smoothie(combo1, [strawberry, orange, banana], 2),          
       smoothie(combo2, [banana, orange], 5),         
       smoothie(combo3, [orange, peach, banana], 2),          
       smoothie(combo4, [guava, mango, papaya, orange],1),         
       smoothie(combo5, [grapefruit, banana, pear],1) ]).

more_than_four(StoreName):-
    store(StoreName, _, ListSmoothies), 
    length(ListSmoothies, Size),
    Size >= 4.

ratio(StoreName, R):-
    store(StoreName, ListEmployees, ListSmoothies), 
    length(ListSmoothies, SizeSmoothie),
    length(ListEmployees,  SizeEmployee),
    R is SizeEmployee / SizeSmoothie.
    

average(StoreName, A):-
    store(StoreName, _, ListSmoothies),
    sum(ListSmoothies,N),
    A is N. 

sum([],0).
sum([smoothie(_,_,P)|T], N):-
    sum(T,N1),
    N is N1+P.