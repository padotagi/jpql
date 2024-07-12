package jpql;

import javax.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("tramA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            //member.setType(MemberType.ADMIN);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            member.changeTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            /* Join
            String query = "select m from Member m inner join m.team t";
            //                  + "where m.type = jpql.MemberType.ADMIN";
            List<Member> result = em.createQuery(query, Member.class)
                            .setFirstResult(1)
                            .setMaxResults(10)
                             .getResultList();
           */

           /* CASE */
//           String query =
//                    "select " +
//                        "case when m.age <= 10 then '학생요금' " +
//                            "when m.age >= 60 then '경로요금' " +
//                            "else '일반요금' " +
//                        "end " +
//                    "from Member m";

           /* 사용자 정의 함수 */
            String query = "select function('group_concat', m.username) from Member m";

           List<String> result = em.createQuery(query, String.class)
                           .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }

           em.createQuery(query);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
