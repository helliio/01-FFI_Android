package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Member;

import java.util.List;

/**
 * Created by chun on 2/10/15.
 */

public interface MemberDao {
	public void add(Member member);

	public void delete(Member member);

	public void update(Member member);

	public Member getById(long id);

	public List<Member> getAll();
}
