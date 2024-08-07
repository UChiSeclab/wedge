// No sorcery shall prevail. //           
import java.util.*;
import java.io.*;
public class InVoker {
	//Variables
	static long mod = 1000000007;
	static long mod2 = 998244353;
	static FastReader inp= new FastReader();
	static PrintWriter out= new PrintWriter(System.out);
	public static void main(String args[]) {		    	
	   	InVoker g=new InVoker();
	   	g.main();
	   	out.close();
	}
	
	
	
	//Main
	void main() {
		
		int t=inp.nextInt();
		loop:
		while(t-->0) {
			int n=inp.nextInt();
			int a[]=new int[n];
			boolean gg[]=new boolean[n];
			input(a,n);
			SegTree st=new SegTree(n);
			st.build(a, 1, 0, n-1);
			gg[0]=true;
			boolean vis[]=new boolean[n+1];
			for(int i=0;i<n;i++) {
				if(vis[a[i]]) {
					gg[0]=false;
					break;
				}
				vis[a[i]]=true;
			}
			gg[n-1]=st.query(0, n-1)==1;
			int start=0,end=n-1,k=n-2,req=1;
			while(start<end) {
				if(a[start]==req && st.query(start+1, end)==req+1) {
					start++;
					req++;
					gg[k--]=true;
				}else if(a[end]==req && st.query(start, end-1)==req+1) {
					end--;
					req++;
					gg[k--]=true;
				}else {
					break;
				}
			}
			for(int i=0;i<n;i++) out.print(gg[i]?1:0);
			out.println();
		}
		
	}
	
	class SegTree {
		int n;
		int seg[];
		SegTree(int n){
			this.n=n;
			seg=new int[4*n];
			for(int i=0;i<4*n;i++) seg[i]=Integer.MAX_VALUE;
		}
		void build(int a[], int x,int lx, int rx) {
			if(lx==rx) {
				seg[x]=a[lx];
				return;
			}
			int mid=lx+rx>>1;
			build(a,2*x,lx,mid);
			build(a,2*x+1,mid+1,rx);
			seg[x]=merge(seg[2*x],seg[2*x+1]);
		}
		
		int merge(int x, int y) {
			return Math.min(x, y);
		}
		int query(int l, int r) {
			return query(l,r,1,0,n-1);
		}
		int query(int l, int r, int x, int lx, int rx) {
			if(l<=lx && r>=rx) return seg[x];
			if(l>rx || r<lx) return Integer.MAX_VALUE;
			int mid=lx+rx>>1;
			return merge(query(l,r,2*x,lx,mid),query(l,r,2*x+1,mid+1,rx));
		}
		
	}
	
	
	
	
	
	
	
	/*********************************************************************************************************************************************************************************************************
	 * ti;.                               .:,:i:         :,;;itt;.        fDDEDDEEEEEEKEEEEEKEEEEEEEEEEEEEEEEE###WKKKKKKKKKKKKKKKKKKKKWWWWWWWWWWWWWWWWWWW#WWWWWKKKKKEE      :,:.    f::::. .,ijLGDDDDDDDEEEEEEE*
	 *ti;.                               .:,:i:          .:,;itt;:      GLDEEGEEEEEEEEEEEEEEEEEEDEEEEEEEEEEE#W#WEKKKKKKKKKKKKKKKKKKKKKKKWWWWWWWWWWWWWWWWWWWWWWKKKKKKG.     .::.      f:,...,ijLGDDDDDDDDEEEEEE *
	 *ti;.                               .:,:i:            :,;;iti,    :fDDEEEEEEEEEEEEEEEKEEEEDEEEEEEEEEEEW##WEEEKKKKKKKKKKKKKKKKKKKKKWWWWWWWWWWWWWWWWWWWWWWWKKKKKKEG     .::.       .f,::,ijLGDDDDDDDDEEEEEE *
	 *ti;.                               .:,:i:             .,,;iti;.  LDDEEEEEEEEEEKEEEEWEEEDDEEEEEEEEEEE#WWWEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKWWWWWWWWWWWWWWWWWWWKKKKKEDj    .::.        .:L;;ijfGDDDDDDDDDEEEEE *
	 *ti;.                               .:,:i:              .:,;;iii:LLDEEEEEEEEEEEKEEEEEEEEDEEEEEEEEEEEW#WWEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKWWKWWWWWWWWWWWWWWKKKKKKKEL    .::.         .:;LijLGGDDDDDDDDEEEEE *
	 *ti;.                               .:,:;:                :,;;ittfDEEEEEEEEEEEEEEEEKEEEGEEEEEEEEEEEKWWWEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKWWWWWWWWWWWWWWWKKKKKKKELj   .::.          :,;jffGGDDDDDDDDDEEEE *
	 *ti;.                               .:,:i:                 .,;;tGGDEEEEEEEEEEEKEEEKEEEDEEEEEEEEEEEEWWWEEEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKWWWWWWKWWWWWWKKKKKKKEEL   .::.          .:;itDGGDDDDDDDDDEEEE *
	 *ti;.                               .:::;:                   :;ifDEEEEEEEEEEEEKEEEKEEEEEEEEEEEEEEEWWWEEEEEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKW#WWWKKKKKKKKEEf   .::.           :,itfGEDDDDDDDDDDDEE *
	 *ti;.                               .:::;:                    :GGEEEEEEEEEEEKEKEEKEEEEEEEEEEEEEEEEWWEEEEEEEEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKW#WKKKKKKKKKEEDG  .::.           .,;jfLGKDLDDDDDDEEDD *
	 *ti;.                               .:::;:                     fDEEEEEEKKKKKKKKKEKEEEEEEEEEEEEEEE#WEEEEEEEEEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKW#KKKKKKKKKKEEf .:::.           .,;tfLGDEDDDDDDDDEEE *
	 *ti;.                                :::;:                    fDEEEEEEKKKKKKKKKKWKEEEEEEEEEEEEEEEWKEEEEEEEEEEEEEEEEEEEEKEKKKKKKKKKKKKKKKKKKKKKKKKKKKKW##KKKKKKKKKEEft :::.           .,;tfLGDDDKDDDDDDDDD *
	 *ti;.                                .::;:                    fDEEEEEEKKKKKKKWKKKKKEEEEEEEEEEEEE#WEEWEEEEEDEEDEEEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKKKW#WKKKKKKKKEEGG :,:.           .,;tfLGGDDDKDDDDDDDD *
	 *ti;.                                .:.;:                   tGDEEEEKKKKKKKKKKKKKKKKKEEEEEEEEEEEWEEKWEEEEEEEDEEEEEEEEEEEEEEKEKKKKKKKKKKKKKKKKKKKKKKKKKKWWWKKKKKKKEEDf :::.           .,;tfLGGDDDDEDDDDDDD *
	 *ti;.                                .::;:                   fDEEEEEKKKKKKKKKKKWKKKKKKKKEEEEEEEWWEEWEEEEEEEEEEEEEEEEEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKW##KKKKKKKEEEft.::.           .,;tfLGGDDDDDDEDDDDD *
	 *ti;.                                .:.;:                  tGDEEEKKKKKKKKKKKKKKKKKKKKKKEKEEEEE#EEWWEEEEEEEEEEEEEEEEEEEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKW#WKKKKKKEEEGD:::.           .,;tfLGGDDDDDDDEDDDD *
	 *ti;.                                .:.,.                  LDEEEEKKKKKKKKKKWKWKKKKKKKKKKKKEEEKWEKW#EEEEEEEEEEEEEEEEKEEEEEEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKW##KKKKKKEEEEf,,:.           .,;tfLGGDDDDDDDDEDDD *
	 *ti;.                               ..:.,.                 LGDEEEEKKKKKKKKKKWKKKKKKKKKKKKKKKKKWEEW#WEEEEEEEEEEEEEEEKEEEEEEEEEEEEEEEEEEEKEKKKKKKKKKKKKKKKK##KKKKKEEEEEfi;,.           .,;tfLGGDDDDDDDDDKDD *
	 *tt;.                                .:.,:                 jDEEEEKKKKKKKKKKWWKKKKKKKKKKKKKKKKKWKE#WWEEEEEEEEEEEEEEWEEEEEEEEEEEEEEEEEEEEEEEKKKKKKKKKKKKKKKKWWKKKKEEEEDfG;,:           .,;tfLGGDDDDDDDDDDKD *
	 *tii,.                               .:.,.                tGDEEEEKKKKKKKKKKWWWKKKKKKKKKKKKKKKWKKWWWKEEEEEEEEEEEEEKEEEEEEEEEEEEEEEEEEEEEEEEEEEEEKKKKKKKKKKKW#KKKKEEEEDGGi;,.          .,;tfLGGDDDDDDDDDDDE *
	 *ti;;,:.                             .:.,:                fDEEEEKKKKKKKKKKKWKKKKKKKKKKKKKKKKKWEK#WWKEEEEEEEEEEEEDEEEEEEEEEEEEEEGEEEEEEEEEEEEEEEEEEEKKKKKKKWWKKEEEEEEDDf;;;,.         .,;tfLGGDDDDDDDDDDDD *
	 *tii;,,:..                           ...,.               ;LEEEEEKKKKKKWKKKKWKKKKKKKKKKKKKKKKKEKKW#WEEEEEEEEEEEEEjEEEEEEEEEKEEEEGEEEEEEEEEKEEEEEEEEEEEEEEEEE#WKEEEEEEDDf;;;;,:        .,itfLGGDDDDDDDDDDDD *
	 *ti;,,,,,:.                          ...,.               LDEEEEKKKKKKKKKKKWWWKKKKKKKKKKKKKKKWKK#W#WEEEEEEEEEEEDDLEEEEEEEEEWEEEEDEEEEEEEEEKEEEEEEEEEEEEEEEEEWWEEEEEEEDDfj,,,,,:.      .,itfGGGDDDDDDDDDDDD *
	 *tii,,,,::::.                        ...,:              .fDEEEEKKKKKKWKKKKWWWKKKKKKKKKKKKKKKEKKW#WWEEEEEEEEEEEKiKEEKEEEEEEWEEEEDEEEEEEEEEEEEEEEEEEEEEEEEEEEWWEEEEEEEDDLD:::,,,:.     .,ijfGGGDDDDDDDDDDDD *
	 *ti;:::::::::..                      .:.,:              LDEEEEKKKKKKKWKKKKWWKKKKKKKKKKKKKKKKtKKWWWWKEEEEEEEEEDiiDEEEEEEEEWWEEEEEEDEEEEEEEEEEEEEEEEEEEEEEEEEEWKEEEEEDDDGL:. .:,,,:    .,ijLGGGDDDDDDDDDDDD *
	 *tt;. .::::::::..                    ...,:             :fDEEEKKKKKKKKKKKKWW#KKKKKKKKKKKKKKKKfKKWWWWKEEEEEEEEDti,DEKEEEEEEWWEEEDEEEEEEEEEKEEEEEEEEEEEEEDEEEEE#WEEEEEGGDGf:.  .:,;,:.  .,ijLGGDDDDDDDDDDDDD *
	 *tt;.   .:::::::..                   ...,:             GDEEEKKKKKKKKWKKKKWWWKKKWKKKKKKKWWWKDEKLWWWWKKEEEEEEDEi,LDEEEEEEEEWWEEEEEEEEEEEEEEEEEEEEEEEEEDEDEEEEEW#EEEEDDDDGf,.    :,,,:...,ijLGGGDDDDDDDDDDDD *
	 *tt;.    .....::::..                 ...,:             fDEEEKKKKKKKKWKKKKWWWWKKKWKKKKKKKKKKfWKiWWW#KKEEEEEEEi;.EDfEEEDEEiWWEEEEEEEEEEEEDGKEEEEEEEEEEDEEEEEEEWWEEEEDDDGGLi.     .,;,:::,ijLGGGDDDDDDDDDDDD *
	 *tt;.      ....:::::.                ...,.            iDEEEEKKKKKKKKWKKWKWWWWWKKWWWKKKKKKKKtWKt#WWWKKEEEEEDji..DDKDDEDEGiWKEEEEEEEEEEDDEjEEEEEEEEEEEDEEEEEEEKWKEEDDDDGGff.      .:,;,,;ijLGGGDDDDDDDDDDDD *
	 *tt;.        ....::::..              .:.,:           .LDEEEKKKKKKKKKKKKWWWWKWWWWWWWWWWKKKKWtKKiDWWWKKKEEEEKi:..DEDDDDDDiiWKEEEEEEEEEEDDEijDEEEEEKEEEEEEEEEEEEWWEEGDDDGGLG.       .:,;;iijLGGGDDDDDDDDDDDD *
	 *tt;.          .....:::..            ...,.           .fEEEEKKKKKKKKWKKKKWWWWWWWWWWWWWWKWKKKiKDiLWWWWKEEEEEi,..fD:DDDDDti;WEEEEEEEEEEKDDi:iDDEEEEWEEEEEEEEEEEE#WEEGDDDDGGG.         :,iitjLGGGDDDDDDDDDDDD *
	 *tti.            .....:::..          ...,.           GDEEEKKKKKKKKKWKKKWWW#WWWWWWWWWWWKWKKjiEjitWWWKKWEEEDi...DDLDDDDji;;WEEEEEEEEEEEDEj.iDDEEEEWEEEEEEEEEEEEWWEEDDDDDDGf.          .,;tjfLGGDDDDDDDDDDDD *
	 *tti.              ....::::..        ...,.           fEEEKKKKKKKKKKKKKKKW#WWWWWWWWWWWWWWWWtiEiiiWWWKKEWKEi....D.EDDDEi;.fWEEEEEEEEEEDDfL.;EDDEEEWEEEEEEEEEEEEWWEEEDDDDDGf.           :;ijfLGGDDDDDDDDDDDD *
	 *tti.                ....::::..      ...,.          LDEEEKKKKKKKKKKKKKKWWWWWWWWWWWWWWWW####WKiiiWWWKKKEEK,...:E:DDDEii..GWEEEEEEEEDWDDiL.,KDDEEEWEEEEEEEEEEEEWWKEEDDDDDGf:           .,itfLGGDDDDDDDDDDDD *
	 *tti.                 .....:::..     ...,.          fDEEEKKKKKKKKKWKKKKWWWWWWWWWWWWW########WLiiWWWKKKEEjD...G,DDDDi;...EWEEEEEEEEDKDEii..LDDEEEWEEEEEEEEEEEEWWWEEDDDDDGfi           .,;tfLGGGDDDDDDDDDDD *
	 *tti.                   .....:::..   ...,.         iGEEEKKKKKKKKKKWKKKKWWWWWWWWWWWW###########KiWWWKKEEE,.D..D.DDDii:...KKEEEEEEEEEDDj:...tEDEEEWEEEEEEEEEEEEWWWEEEDDDDDLL           .,;tjLLGGDDDDDDDDDDD *
	 *tti.                     ....::::......:.         LEEEKKKKKKKKKKWWKKKWWW#KWWWWWWWW#####W####W##KWWKKEEL..:D.jjDDi;,....KKEEEEEEEDfDDi...:iKDEEEWKEEEEEEEEEEEWWWEEEEDDDDLG           .,;tjLLGGDDDDDDDDDDD *
	 *tti.                        ...::::::..,.        :GEEEKKKKKKKKKKKKWWWWW##WWWWWWWWW##WKWK#W#W####WWKEEK.....G.DDti,.....KKEEEEEEDWGDf.,...iKDEEEWWEEEEEEEEEEEW#WEEEEEDDDGL           .,;tjLLGGDDDDDDDDDDD *
	 *tti.                         ....::::::,.        GDEEKKKKKKKKKKKKKWWWW###WWWWWWWWWW#WWWK###W#####WKEKK.....jDDL;;......KKEEEEEEEEEDi.f...;KDEEEWWEEEEEEEEEEEWWWWEEEEEDDGf           .,;tjLLGGDDDDDDDDDDD *
	 *tti.                           ....:::,,.       .LEEEKKKKKWKKKKKWWWWWW###WWWWWWWWWW#WWKW#WW##W#WWWKEKD:....:DD:;......;KEEEEEEEKiDD..f...,KKEEEWWEEEEEEEEEEEWWWWEEEEEDDDf           .:;tjLLGGGDDDDDDDDDD *
	 *tti.                             ...::,,,:.     GDEEKKKKKKKKKKKKWWWWWWW#WWWWWWWWWWW#KjKWWWWWWWWWWWWEK.j,..;fD.;.......fKEEEEEDKG:Di..,....DKEEEWWEEEEEEKEKKKWWWWEEEEEEDDf           .:;tjLLGGDDDDDDDDDDD *
	 *jti.                              ...::,,,,:.  .fEEEKKKKKWKKKKKKWWWWWWW#WWWWWWWWWWK#KKKWWWWWWWWWWWWWK..f:.:G.,:.......EKEEEEEKK;:E:.......fKEEEWWKEKEKKKKKKKW#WWEEEEEEDDf:          .,;tfLLGGDDDDDDDDDDD *
	 *tti.                               ...:,,,;;,: iDEEKKKKKWKKKKKKKWWWWWWW#WWWWWWWWWWK#WDKWWKKWWWWWWWWWE..;G:G..,........KKEEEEEKi.Gi..:.....tKEEKWWWKKKKKKKKKKW##WKEEEEEEDfi          .,;tfLLGGGDDDDDDDDDD *
	 *tti.                               ....::,,;;;,LEEKKKKKKWKKKKKWWWWWWW###WWWWWWWWWWKWWDKWEEEWKKWWWWWKKj.:LG..;.........EKEEEEKG;.G...;.....;KKEKWWWKKKKKKKKKKW##WWKEEEEEDfL          .,;tfLGGGDDDDDDDDDDD *
	 *jti.                                ...::::,;ijDEEKKKKKWKKKKKKWKWWWWW##WWWWWWWWWWWKK#KKGDGDWEEWKKWKKGE,.i;.:.........:EKEEEKE;.:L...j.....,KWEKWWWKKKKKKKKKK####WKKEEEEDLG          .,;tfLGGGGDDDDDDDDDD *
	 *jti.                                ...:...,,;GEEKKKKKWWKKKKKWWWWWWWW###WWWWWWWWWKKKWWKiLGGEDEDEKGKKiEG..;...........jKEEEKK;:.G....,.....:KKEWWWWKKKKKKKWKK####WKKKKEEEGL          .,;tfLGGGGDDDDDDDDDD *
	 *jti.                                ...:.  .:,GEEKKKKKWKKKKKWWWWWWWW####WWWWWWWWWKKKWWKii;fDLGDK: EEi:E:.............EKEEKK;;..L...........KKKWWWWKKKKKKKWKK####WKKKWKEEDf          .,;tfGGGGDDDDDDDDDDD *
	 *jti.                                ...:.    ,EEKKKKKWWKKKKKWWWWWWWWW###WWWWWWWWKKKKfWWLt;i,. fi  EG..D:.............EKEKK;;..t....:.......KWKWWWWKKKKKKKWKK####WKKKWEEEDf:.        .,;tfGGGGDDDDDDDDDDD *
	 *jti.                                ...:.    GEEKKKKKWKKKKKWWWWWWWWW####WWWWWWWKKKKKt;KKEfff     .;t.................KKKKi;:..GtGGfG.......KWWWWWWKKKKKKKWKK###WWWKKKKEEEf,,:       .,;tfGGGGDDDDDDDDDDD *
	 *jti.                                ...:.    GEKKKKKWWKKKKKWWWWWWWWWW##WWWWWWWKKKKKKt;EiKKKK,   ...t................jEKKG;;..,.....,LGi....KWWWWWWKKKKKKWKKKW####WKKKKKEEL,,,:.     .,;tfGGGDDDDDDDDDDDD *
	 *jti.                                ...:.  .GEEKKKKKWKKKKKWWWWWWWWWW###WWWWWWWKKKKKKtiE::tGG........................EEEj;;...,.........:D..DKWWWWWWKKKKK#KKW###W#WKKKKKEEfj:,,,:.   .,;tfGGGDDDDDDDDDDDD *
	 *jti.                                ...:.   DEKKKKKWWKKKKKWWWWWWWWW####WWWWWWWKKKKKKiiE:::.::.......................EEi;;...j.....f......:iDKWWWWWWKKKKK#WW######WKKKKKEELG :,,,,:. .,;tfGGGDDDDDDDDDDDD *
	 *jti.                                ...:.  fEEKKKKWWKKKKWWWWWWWWWWW###WWWWWWWWKKKKKK;tE::..........................DD;.;,.::......;........EWWWWWWWKKKKW#WW#####WWKKKWKKELG  .:,,,:::,;tfGGGDDDDDDDDDDDD *
	 *jti.                                ...:. .DEKEKKKWWKKKKWWWWWWWWWWW###WWWWWWWWKKKKKE,iD::..........................D..,;.,;tLffi...........DWDWWWW#KKKWWWWW#####W#KKKWKEEGL    .:,;,,,;tfGGGDDDDDDDDDDDD *
	 *jti.                                ...:. ;EEKKKKWWKKKKKWWWWWW#WWWW####WWWWWWKKKKKEL:iD:..........................j ..;..;;:.....i,........DKtWWWWWKKWWWWWW#####WWWKKKEKEDf     .:,;;;itfGGGDDDDDDDDDDDD *
	 *jti.                                ...:. DEKKKKKWWKKKKWWWWWWW#WWWW####WWWWWWKKKKKEj:iG...............................:....................GKiWWWWWKKWW#WWW######WWKKKKKEEf       .,;iitfGGGDDDDDDDDDDDD *
	 *jti.                                ...:.:EKKKKKWWKKKKKWWWWWWW#WWW#####WWWWWKWKKKKEi:if:.................................iEKEKKKKKKDj......DKiWWWWWKWK##WW#######WWKKK:KEEL        .:;itfGGGDDDDDDDDDDDD *
	 *jji.                                ...:,DEEKKKWWWKWKKWWWWWWWWWWWW#####WWWWWWWKKKKEi:it..................................j.  KKKKKKKKKKKf..DKiWWWWWKWW##WW#######WWKKK,KEEf         .,;tfGGGDDDDDDDDDDDD *
	 *jji.                                ..L:iDEEKKKWWKKKKKWWWWWWWWWWWW#####WWWWWKWKKKKKi.i;..................................  . KKKWWWWWWWWK..DGiWWWWWKK##WWW#####W#WWKKKjEKEL,        .:;tfGGGDDDDDDDDDDDD *
	 *jji.                               .f:::EEEKKKWWWKKKKKWWWWWWWWWWWW#####WWWWWKWKKKKK;.i,.................................::  KKEKWWWWWWfWK..EiiWWWWWKWW#WW##########KKKD,KELj        .:;tfGGDDDDDDDDDDDDD *
	 *jji.                             .t::::,DEEKKKWWKKKKWWWWWWWWW#WWWW#####WWWWKKWKKKEK;.i:.................................GDDEEEKKKWWWWWtWWD.E;iWWWWWW###WW#########WWKKK.EEDG        .:;tfGGGDDDDDDDDDDDD *
	 *jji.                          . j..::::EKEKKKWWWKKKKWWWWWWWWW#WWW######WWWWKKWKKKEK;.t:.................................ELLEDDEEEWWWWEtWK,.KiiWWWWWW###W##########WWKKK:EEEG     .;tjfLLGDDDDDDDDDDDDDDD *
	 *jji.                          i.::::::,EEEKKWWWKKKKKWWWWWWWWW#WWW#####WWWWWKWKKKKEE,.t..................................DfiEGDDDEEKKKttKWG.KiiWWWWW##WWW##########WWKKK:fEEL  ,fGGGDDDDDDDDEEEDDDDDDDDDD *
	 *jji.                        .;:..:::::DEEEKKWWWKKKKKWWWWWWWWW#WWWW####WWWWWWWKKKKED,.t..................................ifjDDGGEGDKK.ttKKE.DiWWWWW###WW##########WWWKKK:.KELiLGGGGDDDDDDDDDDDDEEEDDDDDDD *
	 *jji.                       i.:.::::::,KEEKKWWWKKKKKKWWWWWWWWW#WWWW####WWWWWWWKKKKEL:.j..................................GGf,;ifLLED .iiKKi:fWWWWWW##W#W##########WWWKKK:.KKLGGDDDDDDDDDDDDDDDDEDDEEDDDDD *
	 *jji.                     .j:.::::::::EEEKKKWWWKKKKKKWWWWWWWW##WWW#####WWWWWWWKKKKKf:.f..................................:EEfftf .,. ;iE,..jWWWWWWW###W############WWKKK,:KKGDDDDDDDDDDDDDDDDDDDDDDDEDDDD *
	 *jji.                    .:.::::::::,,EEEKKWWWKKKKKKKWWWWWWWW##WWW#####WWWWWWWKKKKKt..G....................................EEELL;   .j....tKWWWWWWW################WWWKKtfGKGEDDDDDDDDDDDDDDDDDDDDDDDEEDD *
	 *jji.                   :...:::::::,,jEEKKWWWWKKKKKKWWWWWWWWW##KWW#####KWWWWWWKKKKEi..D....................................:jEEE.........;KKWWWWWWWW#WW##W##########WWKKDLGKEKDDDDDDDDDDDDDDDDDDDDDDDDDED *
	 *jji.                  i:.::::::::,,,EEEKKWWWWKKKKKWWWWWWWWWW##WWW#####WWWWWWWKKKKKi..D......................................:::::......,KKKWWWWWWWWW#####W########WWWKKKGGKKEGGGGGGGGDDDDDDDDDDDDDDDDDDE *
	 *jji.                 i..:::::::::,,tEEKKWWWWKKKKKWWWWWWWWWWW##WW######WWWWWWWKKKKKi..D......................................::::......:EKKKWWWWWWWWWWW##WW########W#WKKWGGKKGGGGGGGGGGGGGGGDDDDDDDDDDDDD *
	 *jji.                .:::::::::::,,,EEEKKWWWWKKKKKWWWWWWWWWWW##WW#####WWWWWWWWKKKKKi..D....................................:::::::::..tELii;KWWWWWWWWWW##WW######WWWWWWKWGGGKGGGGGGGGGGGGGGGGGGGGGGGGGGDG *
	 *jjt.                :.::::::::,,,,fEEKKWWWWKKKKKKWWWWWWWWWW###WW####WWWWWWW#WKKKKKi..D....................................:::::::.:.,;;;;;;,KKWWWWWWWWW#WW########WWWKKWGGGKGGGGGGGGGGGGGGGGGGGGGGGGGGGG *
	 *jji.               ;.::::::::,,,,;EEEKWWWWWKKKKKWWWWWWWWWWWW##WW###WKWWWWWK#WKKKKKi..G......................................:::::::,;;;;:...KKKWWWWWWWWWKWW#######WWWWKKGLGKDGGGGGGLLGGGGGGGGGGGGGGGGGGG *
	 *jjt.              f.:::::::::,,,,fEEKKWWWWWKKKKKWWWWWWWWWWW###WW##WKKWWWWWW#WKKKKK;.jt........i.............................:::::::;j;;....:E.KKKWWWWWWWKWW#####W#WWWWKKLLGWEEGGGGGLGGGGGGGGGGGGGGGGGGGG *
	 *jjt.             ...:::::::,,,,,;DEEKWWWWWKKKKKWWWWWWWWWWWW####WWWKKKWWWWWWWWKKKKK;.E;.........t.............................:::::ii;;.....D...KKWWWWWWWKWW#####WWEWWWKKGGGEKKGGGGGLGGGGGGGGGGGGGLGGGGGG *
	 *fji.             ;.:::::::,,,,,;LEEKKWWWWWKKKKKWWWWWWWWWWWW####KWKKKKWWWWWWWWKKKKKi.D;..........j.............................:::tt;,.....:.....KKWWWWWWKWWWW##WWWGWWWKKGGGGKEGGGGGGGGGGGGGGGGGGGLLGGGGL *
	 *fji.            t::::::::,,,,,,;EEEKWWWWWKKKKKKKWWWWWWWWWWW##WKWKKKKKWWWWWWWWKKKKKi:D;............j...........................::LL;,.............KKWWWWWKWWWWWWWWWGWWWKKGGGGKGGGGGGGGGGGGGGGGGGGGLLGGGGL *
	 *fjt:            .:::::::,,,,,,,DEEKWWWWWWKKKKKKKWWWWWWWWWWWWKKWKKKKKKWWWWK#WWKKKKWitE;........... ............................:G;;:...............KKKWWKKWWWWWWWWWGWWWKKGGGGWGGGGGGGGGGGGGGGGGGGGGGGGGGL *
	 *fjji;:.       .f:::::::,,,,,,,;EEEKWWWWWWKKKKKKWWWWWWWWWWWKKKKKKKKKKKWWKWWWWWKKKKWGKD;........................................L;;..................DKKWKKWWWWWWWWWGWWWKKDGGGKDGGGGGGGGGGGGGGGGGGGGGGGGGG *
	 *fjjtii;,:.     :::::::,,,,,,,;EEEKWWWWWWKKKKKKWWWWWWWWWWKKKKKKKKKKKKWWWWWW#WWKKKKWiEj;......................................:i,;....,...............;KKEKWWWWWWWWWGKWWKKDDGGDEGGGDGGGGGDGGGGGGGGGGGGGGGG *
	 *fjtiiiii;;:.  j::::::,,,,,,,;;EEEKWWWWW#KKKKKWWWWWWWWWKKKKKKWKKKKKKKWWWWWWWWWKKKKWtEL;:....................................;;;:...,;j................:KEEWWWWWWWWWDDWWKKDDDDDKDDDDDDDDDDDDDDDGGGGGGGGGGG *
	 *fjti;;iiii;;,:::::::,,,,,,,,;EEEKWWWWWWWKKKKWWWWWWWWKKKKKKKWKKKKKKKWWWWWWW#W#KKKKWEEii;...................................f;:....,;L...................EEKWWWWWWWWDDWWKKDDDDDKEDDDDDDDDDDDDDDDDDDDDDGGGG *
	 *fjt,,,;;;;ii;f::::::,,,,,,,;;EEKWWWWWWWKKKKKWWWKWWKKKKKKKKKKKKKKKKKWWWWWWW#W#KKKKWKEij;:...............................:G;,.....,;f....................:tKKWWWWWWWDDWWKKDDDDDKKDDDDDDDDDDDDDDDDDDDDDDDDD *
	 *jjt. ..:,;;;;,::::,,,,,,,,;;GEEWWWWWWWWKKKKWKKWKKKKKKKKKKKKKKKKKKKKWWWWWWW#W#KKKKWEDi;j;............................,Li;L;;;..,;;f........................KKKKWWWKDDWWKKDDDGDKKGGGGGGGGDGDDDDDDDDDDDDDDD *
	 *fjt.    .:,,,:::::,,,,,,,;;;EEKWWWWWWWKKKKKKWKKKKKKKKKKKKKKKKKKKKKWKKKWKW##W#KKKKWEti;;G;........................tEEEL;;;;;;;;;;L..........................DKKKKKEDDWWKEDGftiLE;;;;itjLGGGGGGDDDDDDDDDDD *
	 *fjt.       .j::::,,,,,,,;;;DEEWWWWWWWWKKKKWKKKKKKKKKKKKKKKKKKKKKKKWKKWWWK##W#KKKKKEii;;;L;...................iDEEEEEEKKi;j;;;;jD.....:......................,KKKKDGGEKKE:::::;E::::::::::,tLGGDDDDDDDDDD *
	 *fjt.       .;:::,,,,,,,;;;;EEKWWWWWWWWKWKKKKKKKKKKKKKKKWKKKKKKKKKKWKKWWWW#WW#KKKKKKii;;;;f;.............:tDEEEEEKKKKKKKKEti;;;L...............................EEKf;:iKKE::::::E::::::::::::::ifDDDDDDDDD *
	 *fjt:        :::,,,,,,,,;;;DEEWWWWWWWWWEKKKKKKKKKKKKKKKKKKKKKKKKKKWWKKWWWW####KKKKKEiii;;;;f,.........iDEEEEKKKKKKKKKKKKKKKf;iG......i..........................fK::::KKE::::::E::::::::::::::::,tGGDDDDD *
	 *fjt:       t:::,,,,,,;;;;iDEKWWWWWWKEKKKKKKKKKKKKKKKKKKKKKKKKKKKKWWKKWWWW####WKKKKLiii;;;;;L,....,Li;EDEEEEKKKKKKKKKKKKKKKKiG......;:...........................:i:::KKE:::::,E,::::::::::::::::::iGDDDD *
	 *jjt.       f::,,,,,,,;;;;GEEWWWWKEEKEKKKKKKKKKKKKKKKKWKKKKKKKKKKKWWKWWWWW###WWKKKKiii;;;;;;;G,;L;;iiEEEEEEEKKKKKKKKKKKKKWWKE......;t.........:....................j::KEE:,::,,D,,::::,,,,,,:::::::::tDDD *
	 *fjt:.      ,::,,,,,,,;;;;EEWWKEEEEEEKKKKKKKKKKKKKKKKWKKKKKKKKKKKWWKKWWWWW#W#KKKKKKiiiiii;;;;;i;;iiiEEKEEKKWKKKKKKKWKKKKKWWWGi;...;t......,;;;;,....................:,EEE,,,,,,D,,,,,,,,,,,,,,,,::,::::tG *
	 *fjt:.     ,::,,,,,,,;;;;DEKEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKKKKWWWWWWWWW#W#KKKKKKiiiii;;i;;;;;iiiKKKEKKKKWWKWWWWWWKKKKWWWWW;;;:;L.....;;;;;;;;;....................,KEE,,,,,,E,,,,,,,,,,,,,,,,,,,,,,,,; *
	 *fjt:.     f:,,,,,,,;;;;jEDEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKKKKWWWWWWWW#W##KKKKKKiiiiiiii;i;;iiiEKKKKKKKKWKWWWWWWWWKKKWWWWWKi;;i.....,jEEfGi;;;;;...................EED,,,,,,E,,,,,,,,,,,,,,,,,,,,,,,,, *
	 *fjt:.    .f::,,,,,,;;jEEDEEEEEEEEEEKKKKKKKKKKKKKKKWKKKKKKKKKKKKKWWWKWWWWW###KKKKKLiiiiiiiiiiiiiiEEKKKKKKKKWWWWWWWWWWWWKWWWWWWGi;i;,..;jDDDKEGi;;;;;;:................EED,,,,,,D,,,,,,,,,,,,,,,,,,,,,,,,, *
	 *fjt:.  .. ;::,,,,,;;EDDEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKWWKKW#WW####KWKKKiiiiiiiiiiiiijKKKKKKKKKKWWWWWWWWWWWWWWWWWWWWWt;i;;;;i;DDDDDDGi;;;;;;;;:.............EDf;,,,;,G;;;;;;;;;;;;;;;,,,,,,,,,, *
	 *fjt:......:,,,,,,;LDDDEEEEEEEEEEEKKKKKKKKKKKKKKKKWKKKKKKKKKKKKKWWWWKWWWW####KKKKKiiiiiiiiiiijKEKKWKKKKKKKWWWWWWWWWWWWWWWWWWWWWWiLiii;i;DEEEEDDE;i;;;;;;;;;:..........EDi,;;;;;L;;;;;;;;;;;;;;;;;;,,,,,,, *
	 *fjt:......:,,,,,;EDDDEEKEEEEEEEEEKKKKKKKKKKKKKKKWKKKKKKKKKKKKKKWWWWKKWWW##W#KWKKWEiiiiiijGKKKKKWWKKKKKKKKWWWWWWWWWWWWWWWWWWWWWWKi;iiiiDDEEEEEEDEi;;;;;;;;;;;;;,:.....ED;;;;;;;j;;;;;;;;;;;;;;;;;;;;;;;,, *
	 *fjt:.....t,,,,,;DDDDEEEKEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKWWWKKKWWWW##WKWKKWKiiiKKKKKKKKKWWKKKKKKKKWWWWWWWWWWWWWWW#WWWWWWWWWiiiiifLEEEEEEEEDi;i;;;;;;;;;;;;.....DD;;;;;;;i;;;;;;;;;;;;;;;;;;;;;;;;; *
	 *fjt:.....G,,,,,GDDDEEEEEEEEEEEEKKKKKKKKKKKKKKKKWKKKKKKKKKKKKKKKWWWKKKWWW###WKWKKWKitKKKKKKKKKWKKKKKKKKKKWWWWWWWWWWWWWW###WWWWWWWWEiiiiiiiEEEEEEEEDGiiii;;;;;;;;;.....GD;;;;;;;i;;;;;;;;;;;;;;;;;;;;;;;;; *
	 *fjt:.....L,,,,;GDDDEEEEEEEEEEKEKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKWWWWWDGWWW###KKWWKWKKKKKKKKKKKKKKKKKKKKKKKWWWWWWWWWWWWW####WWWWWWWWWiiiiiiiiEEEEEEEEEEDi;i;;;;;;;;.....Lj;;;;;;i;iiiiii;;;;;;ii;;;;;;;;;;; *
	 ***********************************************************************************************************************************************************************************************************/

	void sort(int a[]) {
		ArrayList<Integer> list=new ArrayList<>();
		for(int x: a) list.add(x);
		Collections.sort(list);
		for(int i=0;i<a.length;i++) a[i]=list.get(i);
	}
	void sort(long a[]) {
		ArrayList<Long> list=new ArrayList<>();
		for(long x: a) list.add(x);
		Collections.sort(list);
		for(int i=0;i<a.length;i++) a[i]=list.get(i);
	}
	void ruffleSort(int a[]) {
		Random rand=new Random();
		int n=a.length;
		for(int i=0;i<n;i++) {
			int j=rand.nextInt(n);
			int temp=a[i];
			a[i]=a[j];
			a[j]=temp;
		}
		Arrays.sort(a);
	}
	void ruffleSort(long a[]) {
		Random rand=new Random();
		int n=a.length;
		for(int i=0;i<n;i++) {
			int j=rand.nextInt(n);
			long temp=a[i];
			a[i]=a[j];
			a[j]=temp;
		}
		Arrays.sort(a);
	}
	
    static class FastReader { 
        BufferedReader br; 
        StringTokenizer st;
        public FastReader() { 
            br = new BufferedReader(new InputStreamReader(System.in)); 
        } 
        String next() { 
            while (st==null || !st.hasMoreElements()) { 
                try { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
        int nextInt() { 
            return Integer.parseInt(next()); 
        } 
        long nextLong() { 
            return Long.parseLong(next()); 
        } 
        double nextDouble() { 
            return Double.parseDouble(next()); 
        } 
        String nextLine() { 
            String s=""; 
            try { 
                s=br.readLine(); 
            } 
            catch (IOException e) { 
                e.printStackTrace(); 
            } 
            return s; 
        } 
    } 
    
	long fact[];
	long invFact[];
	void init(int n) {
		fact=new long[n+1];
		invFact=new long[n+1];
		fact[0]=1;
		for(int i=1;i<=n;i++) {
			fact[i]=mul(i,fact[i-1]);
		}
		invFact[n]=power(fact[n],mod-2);
		for(int i=n-1;i>=0;i--) {
			invFact[i]=mul(invFact[i+1],i+1);
		}
	}
	
	long nCr(int n, int r) {
		if(n<r || r<0) return 0;
		return mul(fact[n],mul(invFact[r],invFact[n-r]));
	}
	long add(long a, long b) {
		return (a+b)%mod;
	}
	
	long mul(long a, long b) {
		return a*b%mod;
	}
	
	long power(long x, long y) {
		long gg=1;
		while(y>0) {
			if(y%2==1) gg=mul(gg,x);
			x=mul(x,x);
			y/=2;
		}
		return gg;
	}
    
    // Functions
	static long gcd(long a, long b) { 
		return b==0?a:gcd(b,a%b);
	}
	static int gcd(int a, int b) { 
		return b==0?a:gcd(b,a%b);
	}
	
	void print(int a[]) {
		int n=a.length;
		for(int i=0;i<n;i++) out.print(a[i]+" ");
	}
	
	void print(long a[]) {
		int n=a.length;
		for(int i=0;i<n;i++) out.print(a[i]+" ");
	}
    
    //Input Arrays
    static void input(long a[], int n) {
		for(int i=0;i<n;i++) {
			a[i]=inp.nextLong();
		}
	}
	static void input(int a[], int n) {
		for(int i=0;i<n;i++) {
			a[i]=inp.nextInt();
		}
	}	
	static void input(String s[],int n) {
		for(int i=0;i<n;i++) {
			s[i]=inp.next();
		}
	}
	static void input(int a[][], int n, int m) {
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				a[i][j]=inp.nextInt();
			}
		}
	}
	static void input(long a[][], int n, int m) {
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				a[i][j]=inp.nextLong();
			}
		}
	}
		
}
