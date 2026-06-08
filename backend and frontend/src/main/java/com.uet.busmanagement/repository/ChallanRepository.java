package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Challan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChallanRepository
{

    public boolean save(Challan challan)
    {
        String query = "INSERT INTO challans (challan_number, student_id, amount, due_date, status, issue_date, fine,regnumb) VALUES (?, ?,?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, challan.getChallanNumber());
            stmt.setInt(2, challan.getStudentId());
            stmt.setDouble(3, challan.getAmount());
            stmt.setString(4, challan.getDueDate());
            stmt.setString(5, challan.getStatus() != null ? challan.getStatus() : "PENDING");
            stmt.setString(6, challan.getIssueDate());
            stmt.setDouble(7, challan.getFine());
            stmt.setString(8, challan.getRegnum());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(String challanNumber, String status)
    {
        String query = "UPDATE challans SET status = ? WHERE challan_number = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, status);
            stmt.setString(2, challanNumber);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public List<Challan> findByStudentId(String regnum)
    {
        List<Challan> challans = new ArrayList<>();
        String query = "SELECT * FROM challans WHERE regnumb = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, regnum);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                Challan challan = new Challan();
                challan.setChallanNumber(rs.getString("challan_number"));
                challan.setStudentId(rs.getInt("student_id"));
                challan.setAmount(rs.getDouble("amount"));
                challan.setDueDate(rs.getString("due_date"));
                challan.setStatus(rs.getString("status"));
                challan.setIssueDate(rs.getString("issue_date"));
                challan.setFine(rs.getDouble("fine"));
                challan.setRegnum(rs.getString("regnumb"));

                challans.add(challan);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return challans;
    }
}