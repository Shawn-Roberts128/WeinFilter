clc; clear all; clf; close all;
%% did not work
% 
% 
% B_z = 1;
% E_y = 1;
% q = 1;
% m = 1;
% 
% A_x =@(t, Vel_y) (( q/m ).*B_z * Vel_y );
% A_y =@(t, Vel_x) (( q/m ).*B_z * Vel_x + E_y );
% 
% cond =  [true, true];
% last_pos = [0 , 0 , 0];
% this_pos = [0 , 0 , 0];
% 
% dt = 1;
% while ( sum (cond ) > 0 )
%     
%     dp = 1;
%     
%     t = ( 0: dt : 10 );
%     len = length (t)
%     pos = zeros( 1 ,3 ,len );
%     vel = zeros( 1 ,3 ,len );
%     acc = zeros( 1 ,3 ,len );
%     
%     for i = 1: length( t ) - 2;
%         i_1 = i +1;
%         ii = i + 2;
%         i;
%         t(i);
%         v_y = vel(1,2,i_1);
%         a_y = A_x( i, v_y );
%         
%         acc(1,1,i);
%         acc (1,1,i_1 ) = a_y ;
%         acc (1,2,i_1 ) = A_y( i, vel(1,1,i_1));
%         vel( 1 , 1, ii ) = acc (1,1,i_1) + vel( 1 , 1,i_1 );
%         vel( 1 , 2, ii ) = acc (1,2,i_1) + vel( 1 , 2, i_1 );
%         pos (1,1,ii ) =   vel( 1 , 1, i_1) + pos (1,1,i_1 );
%         pos (1,2,ii ) =   vel( 1 , 1, i_1) + pos (1,2,i_1 );
%         
%     end
%     dt
%     this_pos = pos(:,:,len)
%     last_pos
% 
%     
%     check = this_pos - last_pos
%     
%     for i = (1:2)
%         if (abs (check(1,i)) < dp  )
%             cond(i) = false;
%         elseif ( isnan(check(1,i) )||( isinf(this_pos(1,i)) ) )
%             cond(i) = false;
%         else
%             cond(i) = true;
%         end
%     end
%     last_pos = this_pos;
%     dt = dt/10;
% end
% DID NOT WORK AT ALL!

%% Good models

c1 = 0;
c2 = 0;
w = pi;

E = 0;
B = 1;

yc = @(t) (c1 * cos( w * t ) + c2 * sin( w * t )); % cycloide y fucntion
zc = @(t) ( (-1)* c1 *sin ( w * t ) + c2 * cos ( w * t ));  % cycloide z function

y =@(t) (yc(t) + (E ./ B) .* t - c1) ; % full position function
z =@(t) (zc(t) -c2 ); % full z function 

hold all;
fplot( yc , [0 (2 )]);
fplot( zc, [0 (2 )]);
legend ('y','z'); 

hold off;

figure;
hold all;
fplot( y , [0 10]);
fplot( z , [0 10]);
legend ('y','z'); 

hold off;